package com.czd.reflect.testClass;

import java.lang.reflect.Modifier;

/**
 * 获取Class相关信息
 *
 * @author: czd
 * @create: 2019-12-02 15:07
 */
public class TestClass {
    public static void main(String[] args) {
        // 第一种获取Class方式
        Class c = null;
        try {
            c = Class.forName("com.czd.reflect.testClass.CommonClass");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // com.czd.reflect.testClass.CommonClass
        System.out.println(c.getCanonicalName());
        Class superclass = c.getSuperclass();
        // java.lang.Object
        System.out.println(superclass.getCanonicalName());

        // 第二种获取Class方式
        CommonClass commonClass = new CommonClass("Test", 10);
        Class<? extends CommonClass> aClass = commonClass.getClass();
        // sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(aClass.getClassLoader().toString());

        // 第三种获取Class方式
        Class<CommonClass> commonClass1 = CommonClass.class;
        System.out.println(commonClass1.getName());

        // 实例化Class
        Object instance = null;
        try {
            // 需要有默认的无参数构造器
            instance = c.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }
}
