package com.ljw.springboottimer.scheduledExecutorservice;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 2. ScheduledExecutorService代替Timer（jdk自带）
 * 多线程并行处理定时任务时，Timer运行多个TimeTask时，只要其中之一没有捕获抛出的异常，其它任务便会自动终止运行，
 * 使用ScheduledExecutorService则没有这个问题。
 * @Author: jianweil
 * @date: 2021/12/14 13:42
 */
public class ScheduledExecutorServiceTest {
    public static void main(String[] args) throws InterruptedException {

        //当所有的非守护线程结束时，程序也就终止了，同时会杀死进程中的所有守护线程。反过来说，只要任何非守护线程还在运行，程序就不会终止。
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(false).build());
        System.out.println("begin:" + new Date());
        // 参数：1、任务体 2、首次执行的延时时间 3、任务执行间隔 4、间隔时间单位
        //延迟5秒执行，之后每3秒执行一次
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //do something
                System.out.println("begin:" + new Date());
            }
        }, 5, 3, TimeUnit.SECONDS);
    }
}
