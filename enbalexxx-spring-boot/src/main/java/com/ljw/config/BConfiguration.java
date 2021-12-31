package com.ljw.config;

import com.ljw.service.B;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/31 11:41
 */
@Configuration
public class BConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public B b() {
        System.out.println("开始注入b");
        return new B();
    }
}
