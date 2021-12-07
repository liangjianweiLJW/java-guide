package com.ljw.springbootaop.proxy.cglib;

/**
 * @Description: 将要被代理的类
 * @Author: jianweil
 * @date: 2021/12/7 15:17
 */
public class HelloWorld {
    public String say(boolean say) throws Exception {
        System.out.println("Hello Student");
        if (!say) {
            throw new Exception("回答错误！");
        }
        return "回答正确!";
    }
}
