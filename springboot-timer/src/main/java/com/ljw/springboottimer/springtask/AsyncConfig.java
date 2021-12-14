package com.ljw.springboottimer.springtask;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 解决单线程串行执行 方式1:@Scheduled+@Async+配置异步线程池
 * @Author: jianweil
 * @date: 2021/12/14 14:35
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    /**
     * 定义@Async默认的线程池
     * ThreadPoolTaskExecutor不是完全被IOC容器管理的bean,可以在方法上加上@Bean注解交给容器管理,这样可以将taskExecutor.initialize()方法调用去掉，容器会自动调用
     *
     * @return
     */
    @Override
    public Executor getAsyncExecutor() {
        int processors = Runtime.getRuntime().availableProcessors();
        //常用的执行器
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //核心线程数
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(50);
        //线程队列最大线程数,默认：50
        taskExecutor.setQueueCapacity(100);
        //线程名称前缀
        taskExecutor.setThreadNamePrefix("AsyncConfig-ljw-");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化(重要)
        taskExecutor.initialize();
        return taskExecutor;
    }


}
