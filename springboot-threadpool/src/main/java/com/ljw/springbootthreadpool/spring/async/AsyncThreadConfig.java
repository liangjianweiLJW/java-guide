package com.ljw.springbootthreadpool.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 注解@async配置
 * @Author: jianweil
 * @date: 2021/12/9 11:52
 */
@Slf4j
@EnableAsync
@Configuration
public class AsyncThreadConfig implements AsyncConfigurer {

    @Bean("service2Executor")
    public Executor service2Executor() {
        //Java虚拟机可用的处理器数
        int processors = Runtime.getRuntime().availableProcessors();
        //定义线程池
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //可以查看线程池参数的自定义执行器
        //ThreadPoolTaskExecutor taskExecutor = new VisiableThreadPoolTaskExecutor();
        //核心线程数
        taskExecutor.setCorePoolSize(processors);
        taskExecutor.setMaxPoolSize(100);
        //线程队列最大线程数,默认：100
        taskExecutor.setQueueCapacity(100);
        //线程名称前缀
        taskExecutor.setThreadNamePrefix("my-ljw-");
        //线程池中线程最大空闲时间，默认：60，单位：秒
        taskExecutor.setKeepAliveSeconds(60);
        //核心线程是否允许超时，默认:false
        taskExecutor.setAllowCoreThreadTimeOut(false);
        //IOC容器关闭时是否阻塞等待剩余的任务执行完成，默认:false（必须设置setAwaitTerminationSeconds）
        taskExecutor.setWaitForTasksToCompleteOnShutdown(false);
        //阻塞IOC容器关闭的时间，默认：10秒（必须设置setWaitForTasksToCompleteOnShutdown）
        taskExecutor.setAwaitTerminationSeconds(10);
        /**
         * 拒绝策略，默认是AbortPolicy
         * AbortPolicy：丢弃任务并抛出RejectedExecutionException异常
         * DiscardPolicy：丢弃任务但不抛出异常
         * DiscardOldestPolicy：丢弃最旧的处理程序，然后重试，如果执行器关闭，这时丢弃任务
         * CallerRunsPolicy：执行器执行任务失败，则在策略回调方法中执行任务，如果执行器关闭，这时丢弃任务
         */
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return taskExecutor;
    }

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
        //ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //可以查看线程池参数的自定义执行器
        ThreadPoolTaskExecutor taskExecutor = new VisiableThreadPoolTaskExecutor();
        //核心线程数
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setMaxPoolSize(2);
        //线程队列最大线程数,默认：50
        taskExecutor.setQueueCapacity(50);
        //线程名称前缀
        taskExecutor.setThreadNamePrefix("default-ljw-");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化(重要)
        taskExecutor.initialize();
        return taskExecutor;
    }

    /**
     * 异步方法执行的过程中抛出的异常捕获
     *
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) ->
                log.error("线程池执行任务发送未知错误,执行方法：{}", method.getName(), ex.getMessage());
    }

}
