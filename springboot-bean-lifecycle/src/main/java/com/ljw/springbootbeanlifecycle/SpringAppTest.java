package com.ljw.springbootbeanlifecycle;

import com.ljw.springbootbeanlifecycle.ioc.IocAppConfig;
import com.ljw.springbootbeanlifecycle.lifecycle.service.m1.MyService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/20 19:45
 */
public class SpringAppTest {
    public static void main(String[] args) {

        // 完整的加载过程，当然了解的越多越好
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(IocAppConfig.class);
        MyService myService = (MyService) applicationContext.getBean("myServiceBeanName");
        myService.service();
        applicationContext.registerShutdownHook();
    }
}
