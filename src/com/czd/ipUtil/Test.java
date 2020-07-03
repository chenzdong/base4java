package com.czd.ipUtil;

/**
 * @author: czd
 * @create: 2019-07-22 15:50
 */
public class Test {
    public static void main(String[] args) {
        Integer i = new Integer(1);
        swap(i);
        System.out.println(i);
        String s = ":";
        swap(s);
        System.out.println(s);
        s = "a" + "b" + "c";
        String a = "A";
        String b = "b";
        String c = "C";
        String r = a + b + c;
    }
    public static void swap(Integer source) {
        source = source + 1;
    }
    public static  void swap(String s) {
        System.out.println(s);
        s = "test";
        System.out.println(s);
    }
}
