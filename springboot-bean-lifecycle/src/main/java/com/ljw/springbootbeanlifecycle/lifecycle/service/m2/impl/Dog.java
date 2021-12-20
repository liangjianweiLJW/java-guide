package com.ljw.springbootbeanlifecycle.lifecycle.service.m2.impl;

import com.ljw.springbootbeanlifecycle.lifecycle.service.m2.Animal;
import org.springframework.stereotype.Component;

/**
 * @Description: 狗-服务
 * @Author: jianweil
 * @date: 2021/12/8 9:52
 */
@Component
public class Dog implements Animal {

    public Dog() {
        System.out.println("狗----------构造方法");
    }

    @Override
    public void use() {
        System.out.println("遛狗去了！！！" + Dog.class.getSimpleName());
    }
}
