package com.czd.reflect.proxy;

/**
 * 代理模式测试
 *
 * @author: czd
 * @create: 2018/11/7 14:49
 */
public class ProxyTest {
    interface IHello {
        void sayHello();
    }
    static class Hello implements IHello {
        @Override
        public void sayHello() {
            System.out.println("hello");
        }
    }

    static class HelloProxy implements IHello {
        IHello iHello;
        HelloProxy(IHello iHello) {
            this.iHello = iHello;
        }
        @Override
        public void sayHello() {
            System.out.println("before");
            iHello.sayHello();
            System.out.println("after");
        }
    }

    public static void main(String[] args) {
        IHello hello = new HelloProxy(new Hello());
        hello.sayHello();
    }
}
