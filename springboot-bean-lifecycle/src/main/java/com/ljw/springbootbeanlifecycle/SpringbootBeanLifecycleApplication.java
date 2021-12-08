package com.ljw.springbootbeanlifecycle;

import com.ljw.springbootbeanlifecycle.ioc.IocAppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

//不注入IocAppConfig
@ComponentScan(basePackages = {"com.ljw.springbootbeanlifecycle"}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {IocAppConfig.class})})
@SpringBootApplication
public class SpringbootBeanLifecycleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBeanLifecycleApplication.class, args);
    }

}
