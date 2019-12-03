package com.czd.reflect.testClass;

/**
 * Instance
 *
 * @author: czd
 * @create: 2019-12-02 15:44
 */
public class TestInstance {
    public static void main(String[] args) {
        Object i = Integer.valueOf(2);
        // instanceof
        // false
        boolean isDouble = i instanceof Double;
        System.out.println(isDouble);
        boolean isInteger = i instanceof Integer;
        System.out.println(isInteger);
        boolean isNumber = i instanceof Number;
        System.out.println(isNumber);
        boolean isSerialize = i instanceof java.io.Serializable;
        System.out.println(isSerialize);

        // true
        System.out.println(Integer.class.isInstance(i));
        // true
        System.out.println(Number.class.isInstance(i));
        // false
        System.out.println(Double.class.isInstance(i));
        // true
        System.out.println(Object.class.isInstance(i));


        // true
        System.out.println(Integer.class.isAssignableFrom(Integer.class));
        // true
        System.out.println(Number.class.isAssignableFrom(Integer.class));
        // false
        System.out.println(Double.class.isAssignableFrom(Integer.class));
        // true
        System.out.println(Object.class.isAssignableFrom(Integer.class));
        // false
        System.out.println(Integer.class.isAssignableFrom(Number.class));



    }
}
