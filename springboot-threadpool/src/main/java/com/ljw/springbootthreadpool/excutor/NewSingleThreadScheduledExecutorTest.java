package com.ljw.springbootthreadpool.excutor;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 单线程可执行周期性任务的线程池
 * @Author: jianweil
 * @date: 2021/12/8 17:54
 */
public class NewSingleThreadScheduledExecutorTest {
    //单线程可执行周期性任务的线程池
    static ScheduledExecutorService singleScheduledExecutor = Executors.newSingleThreadScheduledExecutor();


    public static void main(String[] args) {
        testSingleScheduledExecutor();
    }

    //测试单线程可周期执行的线程池
    private static void testSingleScheduledExecutor() {
        for (int i = 0; i < 3; i++) {
            final int index = i;
            //scheduleWithFixedDelay 固定的延迟时间执行任务；scheduleAtFixedRate 固定的频率执行任务
            singleScheduledExecutor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    System.out.println(LocalDateTime.now()+Thread.currentThread().getName() + " index:" + index);
                }
            }, 0, 3, TimeUnit.SECONDS);
        }

        try {
            Thread.sleep(4000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("4*10秒后...");

        singleScheduledExecutor.shutdown();
    }

}
