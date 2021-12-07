package com.ljw.springbootaop.proxy.cglib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description: 测试cglib：匿名内部
 * @Author: jianweil
 * @date: 2021/12/7 15:25
 */
@Slf4j
public class Enhancer2Test {

    /**
     * 前置通知
     */
    private static void before() {
        log.info("before method invoke...");
    }

    /**
     * 后置通知
     */
    private static void after() {
        log.info("after method invoke...");
    }

    /**
     * 异常通知
     */
    private static void exception() {
        log.info("exception method invoke...");
    }

    /**
     * 方法返回前通知
     */
    private static void beforeReturning() {
        log.info("beforeReturning method invoke...");
    }

    public static void main(String[] args) throws Exception {
        HelloWorld hello = new HelloWorld();
        //还有其他的回调方法，这里测试方法拦截器回调：MethodInterceptor
        HelloWorld proxyHW = (HelloWorld) Enhancer.create(hello.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object result = null;
                try {
                    //前置通知
                    before();
                    result = method.invoke(hello, objects);
                    //后置通知
                    after();
                } catch (Exception e) {
                    //异常通知
                    exception();
                } finally {
                    //方法返回前通知
                    beforeReturning();
                }

                return result;
            }
        });
        String result = proxyHW.say(true);
        //String result = proxyHW.say(false);
        System.out.println(result);
    }
}
