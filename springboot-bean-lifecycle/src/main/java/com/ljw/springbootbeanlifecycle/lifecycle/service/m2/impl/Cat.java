package com.ljw.springbootbeanlifecycle.lifecycle.service.m2.impl;

import com.ljw.springbootbeanlifecycle.lifecycle.service.m2.Animal;
import org.springframework.stereotype.Component;


/**
 * @Description: 猫服务
 * @Author: jianweil
 * @date: 2021/12/8 9:51
 */
@Component
public class Cat implements Animal {
    public Cat() {
        System.out.println("猫----------构造方法");
    }

    @Override
    public void use() {
        System.out.println("遛猫去了!!" + Cat.class.getSimpleName());
    }
}
