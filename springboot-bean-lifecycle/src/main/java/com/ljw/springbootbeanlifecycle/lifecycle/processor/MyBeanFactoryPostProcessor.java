package com.ljw.springbootbeanlifecycle.lifecycle.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/20 17:23
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("0. [容器级别只调用一次] 调用 BeanFactoryPostProcessor.postProcessBeanFactory() 方法");
    }
}
