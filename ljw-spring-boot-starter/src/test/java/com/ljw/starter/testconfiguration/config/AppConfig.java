package com.ljw.starter.testconfiguration.config;

import com.ljw.starter.testconfiguration.service.Mybean;
import com.ljw.starter.testconfiguration.service.YourBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * 1. @Configuration(proxyBeanMethods = false):配置文件里的@Bean方法不代理，直接返回新对象
 * MyBean construct......
 * MyBean PostConstruct ....
 * ==========
 * MyBean construct...... ////这里为false则方法不代理，直接调用方法，所有这里会执行一次实例化，但是没有bean生命周期
 * YourBean construct...
 * YourBean PostConstruct...
 * Hi MyBean ,hello world!
 * Hi YourBean ,hello world!
 * <p>
 * 2.@Configuration(proxyBeanMethods = true)
 * MyBean construct......
 * MyBean PostConstruct ....
 * ==========
 * YourBean construct...
 * YourBean PostConstruct...
 * Hi MyBean ,hello world!
 * Hi YourBean ,hello world!
 * @Author: jianweil
 * @date: 2021/12/30 13:34
 */
@Configuration(proxyBeanMethods = false)
public class AppConfig {

    //放一份myBean到ioc容器
    @Bean
    public Mybean myBean() {
        return new Mybean();
    }

    //放一份yourBean到ioc容器
    @Bean
    public YourBean yourBean() {
        System.out.println("==========");
        //注意：@Configuration(proxyBeanMethods = false)：myBean()方法不代理，直接调用
        //注意：@Configuration(proxyBeanMethods = true)：myBean()方法代理，从ioc容器拿
        return new YourBean(myBean());
    }
}
