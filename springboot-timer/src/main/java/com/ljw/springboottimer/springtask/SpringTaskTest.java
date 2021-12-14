package com.ljw.springboottimer.springtask;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description: 3. Spring Task：（spring-boot-starter依赖）
 * 3.1 单线程运行 @Scheduled：使同一个线程中串行执行，如果只有一个定时任务，这样做肯定没问题，当定时任务增多，如果一个任务卡死，会导致其他任务也无法执行。
 * 3.2 多线程运行 @Scheduled+配置定时器的程池
 * 3.3 多线程运行 @Scheduled+@Async+配置异步线程池
 * <p>
 * 如果同时配置了配置定时器的程池和配置异步线程池，并且注解使用了@Scheduled+@Async，则定时任务使用的线程池为：配置异步线程池
 * 上面两种多线程运行方式建议选择3.2
 * @Author: jianweil
 * @date: 2021/12/14 13:58
 */
@Component
@EnableScheduling
public class SpringTaskTest {
    /**
     * 默认是fixedDelay 上一次执行完毕时间后执行下一轮
     */
    @Scheduled(cron = "0/5 * * * * *")
    @Async
    public void run() throws InterruptedException {
        Thread.sleep(6000);
        System.out.println(Thread.currentThread().getName() + "=====>>>>>使用cron  {}" + (System.currentTimeMillis() / 1000));
    }

    /**
     * fixedRate:上一次开始执行时间点之后5秒再执行
     */
    @Scheduled(fixedRate = 5000)
    @Async
    public void run1() throws InterruptedException {
        Thread.sleep(6000);
        System.out.println(Thread.currentThread().getName() + "=====>>>>>使用fixedRate  {}" + (System.currentTimeMillis() / 1000));
    }

    /**
     * fixedDelay:上一次执行完毕时间点之后5秒再执行
     */
    @Scheduled(fixedDelay = 5000)
    @Async
    public void run2() throws InterruptedException {
        Thread.sleep(7000);
        System.out.println(Thread.currentThread().getName() + "=====>>>>>使用fixedDelay  {}" + (System.currentTimeMillis() / 1000));
    }

    /**
     * 第一次延迟2秒后执行，之后按fixedDelay的规则每5秒执行一次
     */
    @Scheduled(initialDelay = 2000, fixedDelay = 5000)
    @Async
    public void run3() {
        System.out.println(Thread.currentThread().getName() + "=====>>>>>使用initialDelay  {}" + (System.currentTimeMillis() / 1000));
    }


    /**
     * ## @Scheduled参数解析：
     * - cron：通过cron表达式来配置任务执行时间（默认是fixedDelay）
     * - initialDelay ：定义该任务延迟多少时间才开始第一次执行
     * - fixedRate：定义一个按一定频率执行的定时任务。**fixedRate 每次任务结束后会从任务编排表中找下一次该执行的任务，判断是否到时机执行，fixedRate的任务某次执行时间再长也不会造成两次任务实例同时执行，也要等到上次任务完成，判断是否到时机执行，到就立即执行，与线程池无关，除非用了@Async注解，使方法异步，即是使用3.3步骤的配置。（3.2是配置线程池，达不到效果）**
     * - fixedDelay：定义一个按一定频率执行的定时任务。**fixedDelay总是在前一次任务完成后，延时固定时间长度然后再执行下一次任务**
     */
}
