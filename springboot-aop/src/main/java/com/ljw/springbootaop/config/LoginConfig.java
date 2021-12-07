package com.ljw.springbootaop.config;

import com.ljw.springbootaop.model.dto.LoginUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 模拟登录用户
 * @Author: jianweil
 * @date: 2021/12/6 15:26
 */
@Configuration
public class LoginConfig {

    @Bean
    public LoginUser loginUser() {
        return new LoginUser("1", "ljw");
    }
}
