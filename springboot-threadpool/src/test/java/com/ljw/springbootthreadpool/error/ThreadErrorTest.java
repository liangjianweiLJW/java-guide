package com.ljw.springbootthreadpool.error;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @Description: 测试线程池的线程在报错情况下如何运行
 * @Author: jianweil
 * @date: 2021/12/9 15:45
 */
public class ThreadErrorTest {

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(200), new ThreadFactoryBuilder().setNamePrefix("ljw-test-error").build());

    /**
     * execute报错，并且线程因报错而停止并重新创建新的线程执行任务，不能复用
     */
    @Test
    public void testError() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadPoolExecutor.execute(() -> {
                int j = 1 / 0;
            });
        });
    }

    /**
     * submit：吃掉异常，线程复用
     */
    @Test
    public void testErrorSubmit() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadPoolExecutor.submit(() -> {
                System.out.println(Thread.currentThread().getName());
                int j = 1 / 0;
            });
        });
    }





    /**
     * 当业务代码的异常捕获了，线程就可以复用了
     */
    @Test
    public void testTryCatch() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadPoolExecutor.execute(() -> {
                try {
                    int j = 1 / 0;
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + " " + e.getMessage());
                }
            });
        });
    }


    /**
     * 使用setUncaughtExceptionHandler保证线程的所有异常都能捕获（包括业务的异常），兜底的
     * 可以捕获异常，但是并不能复用线程：execute
     */
    private static ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(200),
            new ThreadFactoryBuilder()
                    .setNamePrefix("ljw-test-uncaught")
                    .setUncaughtExceptionHandler((t, e) -> System.out.println("UncaughtExceptionHandler捕获到：" + t.getName() + "发生异常" + e.getMessage()))
                    .build());

    @Test
    public void testExecute() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            threadPoolExecutor2.execute(() -> {
                System.out.println("线程" + Thread.currentThread().getName() + "执行");
                int j = 1 / 0;
            });
        });
    }

    /**
     * 1. setUncaughtExceptionHandler+submit ：可以吃掉异常并复用线程（是吃掉，不报错）
     * 2.  future.get() ： 获取到异常
     */
    @Test
    public void testSubmit() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Future<?> future = threadPoolExecutor2.submit(() -> {
                System.out.println("线程" + Thread.currentThread().getName() + "执行");
                int j = 1 / 0;
            });
            try {
                future.get();
            } catch (InterruptedException e) {
                System.out.println("捕获到异常：" + e.getMessage());
            } catch (ExecutionException e) {
                System.out.println("捕获到异常：" + e.getMessage());
            }
        });
    }


}
