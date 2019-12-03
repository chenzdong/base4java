package com.czd.reflect.testClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 测试反射获取Member相关
 *
 * @author: czd
 * @create: 2019-12-03 13:54
 */
public class TestMember {
    public static void main(String[] args) {
        CommonClass common = new CommonClass();
        Class c = common.getClass();

//        // field
//        try {
//            Field className = c.getDeclaredField("name");
//            System.out.println(className.getName());
//            System.out.println(className.getType());
//            System.out.println(className.getModifiers());
//            className.setAccessible(true);
//            System.out.println(className.get(common));
//            className.set(common, "hhhhh");
//            System.out.println(className.get(common));
//
//            Field[] fields = c.getDeclaredFields();
//            for (Field field : fields) {
//                System.out.println(field.getName());
//            }
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }

        // method
        Method method = null;
        try {
            method = c.getMethod("setName", String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println(method.getModifiers());
        System.out.println(method.getName());
        System.out.println(method.getReturnType());
        System.out.println(method.getTypeParameters());
        try {
            method.invoke(common, "czd");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(common.getName());

        Method[] methods = c.getDeclaredMethods();
        for (Method method1 : methods) {
            System.out.println(method1.getName());
        }

        // constructor
        Constructor[] constructors = c.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(constructor.getName());
            System.out.println(constructor.getParameterCount());
        }
    }
}
