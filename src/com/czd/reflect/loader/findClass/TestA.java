package com.czd.reflect.loader.findClass;

/**
 * @author: czd
 * @create: 2020/12/30 14:32
 */
public class TestA {
    public static void main(String[] args) {
        TestA testA = new TestA();
        testA.hello();
    }

    private void hello() {
        System.out.println("testA: " + this.getClass().getClassLoader());
        System.out.println("testA parent: " + this.getClass().getClassLoader().getParent());
        System.out.println("testA parent parent: " + this.getClass().getClassLoader().getParent().getParent());
        System.out.println("testA parent parent parent: " + this.getClass().getClassLoader().getParent().getParent().getParent());
        TestB testB = new TestB();
        testB.hello();
    }
}
