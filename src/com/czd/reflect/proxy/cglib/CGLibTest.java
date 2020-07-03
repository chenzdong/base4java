package com.czd.reflect.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 反射接口
 * @author: czd
 * @create: 2019-10-15 15:02
 */
public class CGLibTest {
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
    static class  CGLibProxyHandler implements MethodInterceptor {
        Object target;
        Object invoke(Object objectTarget) {
            this.target = objectTarget;
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(objectTarget.getClass());
            enhancer.setCallback(this);
            return enhancer.create();
        }
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("begin");
            method.invoke(target, null);
            System.out.println("end");
            return null;
        }
    }

    public static void main(String[] args) {
        IHello hello = (IHello) new CGLibProxyHandler().invoke(new Hello());
        hello.sayHello();
    }

}
