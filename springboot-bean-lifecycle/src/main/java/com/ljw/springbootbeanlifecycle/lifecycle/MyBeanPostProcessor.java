package com.ljw.springbootbeanlifecycle.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Description: 后置bean的初始化器：所有的bean都会拦截执行
 * @Author: jianweil
 * @date: 2021/12/8 9:46
 *
 */
@Component
@Slf4j
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //这里过滤掉springboot自动配置的bean，只打印我们项目的bean情况
        if (bean.getClass().getName().contains("com.ljw.springbootbeanlifecycle")) {
            log.info("======================postProcessBeforeInitialization==============================");
            log.info("BeanPostProcessor调用postProcessBeforeInitialization方法：beanName为{}",beanName);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().getName().contains("com.ljw.springbootbeanlifecycle")) {
            log.info("======================postProcessAfterInitialization==============================");
            log.info("BeanPostProcessor调用postProcessAfterInitialization方法：beanName为{}",beanName);
        }
        return bean;
    }
}
