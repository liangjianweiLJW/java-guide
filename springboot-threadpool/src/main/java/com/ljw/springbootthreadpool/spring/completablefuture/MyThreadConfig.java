package com.ljw.springbootthreadpool.spring.completablefuture;

import com.ljw.springbootthreadpool.spring.completablefuture.ThreadPoolConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * @Description: 线程池配置类:根据不同业务定义不同的线程池配置
 * @Author: jianweil
 * @date: 2021/12/9 10:48
 */
@EnableConfigurationProperties(ThreadPoolConfigProperties.class)
@Configuration
public class MyThreadConfig {

    @Bean("service1Executor")
    public ThreadPoolExecutor service1Executor(ThreadPoolConfigProperties pool) {
        return new ThreadPoolExecutor(
                pool.getCoreSize(),
                pool.getMaxSize(),
                pool.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

}
