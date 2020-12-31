package com.czd.reflect.loader.findClass;

/**
 * @author: czd
 * @create: 2020/12/30 14:34
 */
public class TestB {
    public void hello() {
        System.out.println("TestB: " + this.getClass().getClassLoader());
        System.out.println("TestB parent: " + this.getClass().getClassLoader().getParent());
        System.out.println("TestB parent parent: " + this.getClass().getClassLoader().getParent().getParent());
    }
}
