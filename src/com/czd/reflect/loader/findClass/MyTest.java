package com.czd.reflect.loader.findClass;

import java.lang.reflect.Method;

/**
 * 使用findClass来定义类加载器，先委托父类加载，如果父类加载不了就自己进行加载
 * 所以TestA为自定义加载器，TestB为自定义加载器的父类加载器加载
 * @author: czd
 * @create: 2020/12/30 14:48
 */
public class MyTest {
    public static void main(String[] args) throws Exception{
        MyClassLoaderParentFirst myClassLoaderParentFirst = new MyClassLoaderParentFirst();
        Class testA = myClassLoaderParentFirst.findClass("com.czd.reflect.loader.findClass.TestA");
        Method method = testA.getDeclaredMethod("main", String[].class);
        method.invoke(null, new Object[]{args});
    }
}
