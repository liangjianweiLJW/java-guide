package com.ljw.starter.testconfiguration;

import com.ljw.starter.testconfiguration.config.AppConfig;
import com.ljw.starter.testconfiguration.service.Mybean;
import com.ljw.starter.testconfiguration.service.YourBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description: 测试@Configuration(proxyBeanMethods = false)的proxyBeanMethods参数作用
 * @Author: jianweil
 * @date: 2021/12/30 13:33
 */
public class MainTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        Mybean myBean = ctx.getBean(Mybean.class);
        myBean.sayHello();
        //Mybean myBean2 = ctx.getBean(Mybean.class);
        //System.out.println(myBean==myBean2);
        YourBean yourBean = ctx.getBean(YourBean.class);
        yourBean.sayHello();
    }

}
