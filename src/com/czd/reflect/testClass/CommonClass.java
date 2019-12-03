package com.czd.reflect.testClass;

/**
 * 普通类 用于试验Class文件相关属性
 *
 * @author: czd
 * @create: 2019-12-02 15:08
 */

public class CommonClass {
    public String className;
    private String name;
    private int age;
    public CommonClass() {
        this.name = "Test";
        this.age = 100;
        this.className="CommonClass";
    }
    public CommonClass(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
