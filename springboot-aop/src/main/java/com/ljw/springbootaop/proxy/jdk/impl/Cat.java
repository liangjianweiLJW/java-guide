package com.ljw.springbootaop.proxy.jdk.impl;

import com.ljw.springbootaop.proxy.jdk.Animal;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/7 16:05
 */
public class Cat implements Animal {
    @Override
    public void doWhat(boolean isDo) throws Exception {
        if (!isDo) {
            System.out.println("Cat只会吃！");
            throw new Exception("Cat只会吃");
        } else {
            System.out.println("Cat会喵喵喵");
        }
    }
}
