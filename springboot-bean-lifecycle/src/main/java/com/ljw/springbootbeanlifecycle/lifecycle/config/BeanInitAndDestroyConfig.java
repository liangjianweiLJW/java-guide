package com.ljw.springbootbeanlifecycle.lifecycle.config;

import com.ljw.springbootbeanlifecycle.lifecycle.MyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

/**
 * @Description: @Bean用法及生命周期
 * @Author: jianweil
 * @date: 2021/12/8 9:41
 */
@Configuration
public class BeanInitAndDestroyConfig {

    /**
     * @return 这里没有指定bean名字，默认是方法名
     */
    @Description("测试bean的生命周期")
    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public MyService myDefineBeanName() {//入参数可注入其他依赖
        return new MyService();
    }
}
