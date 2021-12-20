
package com.ljw.springbootbeanlifecycle;

import com.ljw.springbootbeanlifecycle.ioc.IocAppConfig;
import com.ljw.springbootbeanlifecycle.lifecycle.service.m2.impl.Cat;
import com.ljw.springbootbeanlifecycle.lifecycle.service.m2.impl.Dog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/8 11:54
 */
@Slf4j
public class IocApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(IocAppConfig.class);
        Dog dog = applicationContext.getBean(Dog.class);
        log.info(dog.toString());
        try {
            Cat cat = applicationContext.getBean(Cat.class);
        } catch (BeansException e) {
            log.info("【ioc获取不了beanName为cat的bean,因为扫描时配置过滤掉了@com.ljw.core.demo.ioc.IocAppConfig】" + e.getMessage());
        } finally {
            applicationContext.close();
        }

    }
}
