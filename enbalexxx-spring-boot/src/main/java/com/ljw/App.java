package com.ljw;

import com.ljw.annotation.EnableDefinedBean;
import com.ljw.properties.User;
import com.ljw.service.*;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/31 11:32
 *
 */
@SpringBootApplication
//开启注入Non.class
//@EnableDefinedBean(isBeanNon = true)
@EnableDefinedBean
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        System.out.println("-->" + context.getBean(A.class));
        System.out.println("-->" + context.getBean(B.class));
        System.out.println("-->" + context.getBean(C.class));
        System.out.println("-->" + context.getBean(D.class));
        System.out.println("-->" + context.getBean(ImportSourceService.class));
        System.out.println("-->" + context.getBean(User.class));

        try {
            Non bean = context.getBean(Non.class);
            System.out.println("-->" + bean);
        } catch (BeansException e) {
            System.err.println("-->没有注入Non");

        }
      /*  String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }*/
    }
}
