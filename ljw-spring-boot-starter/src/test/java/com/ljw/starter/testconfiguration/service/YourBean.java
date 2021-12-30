package com.ljw.starter.testconfiguration.service;

import javax.annotation.PostConstruct;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/30 13:34
 */
public class YourBean {

    public Mybean myBean;

    public YourBean(Mybean myBean) {
        System.out.println("YourBean construct...");
        this.myBean = myBean;
    }

    @PostConstruct
    public void init() {
        System.out.println("YourBean PostConstruct...");
    }

    public void sayHello() {
        System.out.println("Hi YourBean ,hello world!");
    }
}
