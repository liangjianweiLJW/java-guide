package com.ljw.springbootbeanlifecycle.lifecycle.service.impl;

import com.ljw.springbootbeanlifecycle.lifecycle.service.Animal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @Description: 猫服务
 * @Author: jianweil
 * @date: 2021/12/8 9:51
 */
@Slf4j
@Component
public class Cat implements Animal {
    @Override
    public void use() {
        log.info("遛猫去了!!" + Cat.class.getSimpleName());
    }
}
