package com.ljw.springbootbeanlifecycle.lifecycle.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Description: 后置bean的初始化器：所有的bean都会拦截执行
 * @Author: jianweil
 * @date: 2021/12/8 9:46
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //这里过滤掉springboot自动配置的bean，只打印我们项目的bean情况
        if ("myServiceBeanName".equals(beanName) || "dog".equals(beanName)) {
            System.out.println("9. [容器级别每个bean都回调] 调用 BeanPostProcessor.postProcessBeforeInitialization 方法：beanName为" + beanName);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("myServiceBeanName".equals(beanName) || "dog".equals(beanName)) {
            System.out.println("13. [容器级别每个bean都回调] 调用 BeanPostProcessor.postProcessAfterInitialization 方法：beanName为" + beanName);
        }
        return bean;
    }
}
