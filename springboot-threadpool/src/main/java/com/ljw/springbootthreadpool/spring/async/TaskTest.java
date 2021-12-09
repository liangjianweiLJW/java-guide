package com.ljw.springbootthreadpool.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/9 11:48
 */
@Component
@Slf4j
public class TaskTest implements ApplicationRunner {
    @Resource
    private Service2 service2;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始做任务一");
        service2.service2Executor();
        log.info("开始做任务二");
        for (int i = 0; i < 3; i++) {
            service2.defaultThread();
        }

    }
}
