package com.ljw.springbootbeanlifecycle.lifecycle.service.impl;

import com.ljw.springbootbeanlifecycle.lifecycle.service.Animal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description: 狗-服务
 * @Author: jianweil
 * @date: 2021/12/8 9:52
 */
@Slf4j
@Component
public class Dog implements Animal {

    public Dog() {
        log.info("狗----------构造方法");
    }

    @Override
    public void use() {
        log.info("遛狗去了！！！" + Dog.class.getSimpleName());
    }
}
