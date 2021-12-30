package com.ljw.starter.testconfiguration.service;

import javax.annotation.PostConstruct;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/30 13:34
 */
public class Mybean {

    public Mybean() {
        System.out.println("MyBean construct......");
    }

    @PostConstruct
    public void init() {
        System.out.println("MyBean PostConstruct ....");
    }

    public void sayHello() {
        System.out.println("Hi MyBean ,hello world!");
    }

}
