package com.ljw2.web;

import com.ljw.starter.service.HelloService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/30 15:26
 */
@Service
public class TestController implements CommandLineRunner {

    /**
     * 注入自定义starter服务
     */
    @Resource
    private HelloService helloService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(helloService.hello());
    }
}
