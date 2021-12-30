package com.ljw.starter.testconditional.config;

import com.ljw.starter.testconditional.conditonal.LinuxCondition;
import com.ljw.starter.testconditional.conditonal.WindowsCondition;
import com.ljw.starter.testconditional.service.TypeService;
import com.ljw.starter.testconditional.service.impl.LinuxServiceImpl;
import com.ljw.starter.testconditional.service.impl.WindowServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 配置类
 * @Author: jianweil
 * @date: 2021/12/30 11:22
 */
@Configuration
public class TypeConfig {

    @Bean
    @Conditional(WindowsCondition.class)
    public TypeService window() {
        return new WindowServiceImpl();
    }

    @Bean
    @Conditional(LinuxCondition.class)
    public TypeService linux() {
        return new LinuxServiceImpl();
    }
}
