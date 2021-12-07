package com.ljw.springbootaop.proxy.cglib;

/**
 * @Description: 测试cglib
 * @Author: jianweil
 * @date: 2021/12/7 15:25
 */
public class EnhancerTest {
    public static void main(String[] args) throws Exception {
        HelloWorld hello = new HelloWorld();
        ProxyFactory proxy = new ProxyFactory();
        HelloWorld world = (HelloWorld) proxy.createProxy(hello);
        String result = world.say(true);
        //String result = world.say(false);
        System.out.println(result);
    }
}
