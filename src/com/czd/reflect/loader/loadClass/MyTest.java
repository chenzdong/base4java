package com.czd.reflect.loader.loadClass;

import java.lang.reflect.Method;

/**
 * 使用loadClass来定义类加载器，自己写加载规则
 * 所以TestA、TestB都为自定义加载器
 *
 * @author: czd
 * @create: 2020/12/30 14:48
 */
public class MyTest {
    public static void main(String[] args) throws Exception {
        MyClassLoaderCustom myClassLoaderCustom = new MyClassLoaderCustom(Thread.currentThread().getContextClassLoader().getParent());
        Class testA = myClassLoaderCustom.loadClass("com.czd.reflect.loader.loadClass.TestA");
        Method method = testA.getDeclaredMethod("main", String[].class);
        method.invoke(null, new Object[]{args});
    }
}
