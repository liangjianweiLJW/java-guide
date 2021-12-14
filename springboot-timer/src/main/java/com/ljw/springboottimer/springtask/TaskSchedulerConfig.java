package com.ljw.springboottimer.springtask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @Description: 解决单线程串行执行 方式2:@Scheduled+配置定时器的程池
 * @Author: jianweil
 * @date: 2021/12/14 14:44
 */
@Configuration
public class TaskSchedulerConfig {
    /**
     * 初始化了一个线程池大小为 5  的 TaskScheduler, 避免了所有任务都用一个线程来执行
     *
     * @return
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(5);
        taskScheduler.setThreadNamePrefix("TaskSchedulerConfig-ljw");
        return taskScheduler;
    }
}
