package com.czd.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理测试 针对接口
 *
 * @author: czd
 * @create: 2018/11/7 14:33
 */
public class DynamicProxyTest {
    interface  IHello {
        void sayHello();
        void sayNothing();
    }

    static class Hello implements IHello {
        @Override
        public void sayHello() {
            System.out.println("Hello man!");
        }
        @Override
        public void sayNothing() {
            System.out.println("I don't want to say anything");
        }
    }

    static class DynamicProxy implements InvocationHandler {
        Object originalObj;
        Object bind(Object originalObj) {
            this.originalObj = originalObj;
            return Proxy.newProxyInstance(originalObj.getClass().getClassLoader(),
                    originalObj.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("welcome");
            return  method.invoke(originalObj, args);
        }
    }

    public static void main(String[] args) {
        IHello hello = (IHello) new DynamicProxy().bind(new Hello());
        hello.sayHello();
        hello.sayNothing();
    }
}
