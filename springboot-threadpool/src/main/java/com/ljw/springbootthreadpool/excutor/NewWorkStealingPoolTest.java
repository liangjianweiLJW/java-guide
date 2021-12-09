package com.ljw.springbootthreadpool.excutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 任务窃取线程池，不保证执行顺序，适合任务耗时差异较大。
 * <p>
 * 线程池中有多个线程队列，有的线程队列中有大量的比较耗时的任务堆积，而有的线程队列却是空的，就存在有的线程处于饥饿状态，当一个线程处于饥饿状态时，它就会去其它的线程队列中窃取任务。解决饥饿导致的效率问题。
 * <p>
 * 默认创建的并行 level 是 CPU 的核数。主线程结束，即使线程池有任务也会立即停止。
 * @Author: jianweil
 * @date: 2021/12/8 17:55
 */
public class NewWorkStealingPoolTest {
    //任务窃取线程池
    static ExecutorService workStealingExecutor = Executors.newWorkStealingPool();

    public static void main(String[] args) {
        testWorkStealingExecutor();
    }

    //测试任务窃取线程池  其中一个任务可以产生其他较小的任务，这些任务被添加到并行处理线程的队列中。如果一个线程完成了工作并且无事可做，则可以从另一线程的队列中"窃取"工作。
    private static void testWorkStealingExecutor() {
        for (int i = 0; i < 20; i++) {//本机 CPU 8核，这里创建10个任务进行测试
            final int index = i;
            workStealingExecutor.execute(new Runnable() {
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
            Thread.sleep(4000*10);//这里主线程不休眠，不会有打印输出
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("4*10秒后...");

//  workStealingExecutor.shutdown();
    }
}
