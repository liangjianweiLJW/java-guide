package com.ljw.springbootthreadpool.excutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 单线程的线程池，线程异常结束，会创建一个新的线程，能确保任务按提交顺序执行
 * @Author: jianweil
 * @date: 2021/12/8 17:54
 */
public class NewSingleThreadExecutorTest {
    //单线程的线程池，线程异常结束，会创建一个新的线程，能确保任务按提交顺序执行
    static ExecutorService singleExecutor = Executors.newSingleThreadExecutor();


    public static void main(String[] args) {
        testSingleExecutor();
    }

    //测试单线程的线程池
    private static void testSingleExecutor() {
        for (int i = 0; i < 3; i++) {
            final int index = i;
            singleExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " index:" + index);
                }
            });
        }

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("4秒后...");

        singleExecutor.shutdown();
    }
}
