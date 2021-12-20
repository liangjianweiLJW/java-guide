package com.ljw.springbootbeanlifecycle.lifecycle.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/20 17:20
 */
@Component
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if ("myServiceBeanName".equals(beanName) || "dog".equals(beanName)) {
            System.out.println("============================InstantiationAwareBeanPostProcessor-开始======================");
            System.out.println("1. [容器级别每个bean都回调] 调用 InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation() 方法：beanName为"+beanName);
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if ("myServiceBeanName".equals(beanName) || "dog".equals(beanName)) {
            System.out.println("3. [容器级别每个bean都回调] 调用 InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation() 方法：beanName为"+beanName);
        }
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if ("myServiceBeanName".equals(beanName) || "dog".equals(beanName)) {
            System.out.println("4. [容器级别每个bean都回调] 调用 InstantiationAwareBeanPostProcessor.postProcessProperties() 方法：beanName为"+beanName);
            System.out.println("============================InstantiationAwareBeanPostProcessor-结束======================");

        }
        return null;
    }
}
