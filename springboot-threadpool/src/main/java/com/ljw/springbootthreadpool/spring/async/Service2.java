package com.ljw.springbootthreadpool.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/9 14:00
 */
@Service
@Slf4j
public class Service2 {

    public static Random random = new Random();

    /**
     * 指定线程池service2Executor
     *
     * @throws Exception
     */
    @Async("service2Executor")
    public void service2Executor() throws Exception {
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(1000));
        long end = System.currentTimeMillis();
        log.info("使用线程池service2Executor，耗时：" + (end - start) + "毫秒");
    }


    /**
     * 默认线程池
     */
    @Async
    public void defaultThread() throws Exception {
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(1000));
        long end = System.currentTimeMillis();
        int i = 1 / 0;
        log.info("使用默认线程池，耗时：" + (end - start) + "毫秒");
    }

}
