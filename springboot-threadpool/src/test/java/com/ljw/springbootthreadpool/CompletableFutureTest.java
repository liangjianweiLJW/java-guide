package com.ljw.springbootthreadpool;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 测试CompletableFuture
 * @Author: jianweil
 * @date: 2021/12/9 10:50
 */
@SpringBootTest
public class CompletableFutureTest {

    @Resource
    private ThreadPoolExecutor service1Executor;


    /***
     * 无返回值
     *  runAsync
     */
    @Test
    public void main1() {
        System.out.println("main.................start.....");
        CompletableFuture.runAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
        }, service1Executor);
        System.out.println("main.................end......");
    }

    /**
     * 有返回值
     * supplyAsync
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void main2() throws ExecutionException, InterruptedException {
        System.out.println("main.................start.....");
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }, service1Executor);
        System.out.println("main.................end....." + completableFuture.get());
    }

    /**
     * 方法完成后的感知
     * 有返回值并且有后续操作 whenComplete
     * 感知错误并返回指定值 exceptionally
     */
    @Test
    public void main3() throws ExecutionException, InterruptedException {
        System.out.println("main.................start.....");
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 0;
            System.out.println("运行结果：" + i);
            return i;
        }, service1Executor).whenComplete((result, throwable) -> {
            //whenComplete虽然得到异常信息，但是不能修改返回信息
            System.out.println("异步完成。。。。结果是：" + result + "...异常是：" + throwable);
        }).exceptionally(throwable -> {
            //R apply(T t);
            //exceptionally可以感知错误并返回指定值
            return 0;
        });
        System.out.println("main.................end....." + completableFuture.get());
    }

    /**
     * 方法完成后的处理
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void main4() throws ExecutionException, InterruptedException {
        System.out.println("main.................start.....");
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            //int i = 10 / 2;
            int i = 10 / 5;
            //int i = 10 / 0;
            System.out.println("运行结果：" + i);
            return i;
        }, service1Executor).handle((result, throwable) -> {
            //R apply(T t, U u);
            if (result == 5) {
                return result * 2;
            }
            if (throwable != null) {
                return 8888;
            }
            return 0;
        });
        System.out.println("main.................end....." + completableFuture.get());
    }

    /**
     * 线程串行化方法
     * <p>
     * //.thenApply()上面任务执行完执行+可以拿到结果+可以返回值
     * //.thenAccept()上面任务执行完执行+可以拿到结果
     * //.thenRun() 上面任务执行完执行
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void main5() throws ExecutionException, InterruptedException {
        System.out.println("main.................start.....");
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            return i;
        }, service1Executor).thenRunAsync(() -> {
            System.out.println("任务2启动了。。。。。");
        }, service1Executor);

        CompletableFuture<Void> thenAcceptAsync = CompletableFuture.supplyAsync(() -> {
            System.out.println("thenAcceptAsync当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("thenAcceptAsync运行结果：" + i);
            return i;
        }, service1Executor).thenAcceptAsync(result -> {
            System.out.println("thenAcceptAsync任务2启动了。。。。。上步结果：" + result);
        }, service1Executor);

        CompletableFuture<String> thenApplyAsync = CompletableFuture.supplyAsync(() -> {
            System.out.println("thenApplyAsync当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("thenApplyAsync运行结果：" + i);
            return i;
        }, service1Executor).thenApplyAsync(result -> {
            System.out.println("thenApplyAsync任务2启动了。。。。。上步结果：" + result);
            return "hello" + result * 2;
        }, service1Executor);
        System.out.println("main.................end....." + thenApplyAsync.get());
    }

    /**
     * 两任务组合 都要完成
     * <p>
     * completableFuture.runAfterBoth() 组合两个future
     * completableFuture.thenAcceptBoth() 获取两个future返回结果，无返回值
     * completableFuture.thenCombine() 获取两个future返回结果，有返回值
     */
    @Test
    public void main6() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("任务1运行结果：" + i);
            return i;
        }, service1Executor);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2线程：" + Thread.currentThread().getId());
            System.out.println("任务2运行结果：");
            return "hello";
        }, service1Executor);

        CompletableFuture<Void> runAfterBothAsync = future1.runAfterBothAsync(future2, () -> {
            System.out.println("任务3启动。。。");
        }, service1Executor);

        CompletableFuture<Void> thenAcceptBothAsync = future1.thenAcceptBothAsync(future2, (result1, result2) -> {
            System.out.println("任务4启动。。。结果1：" + result1 + "。。。结果2：" + result2);
        }, service1Executor);

        CompletableFuture<String> thenCombineAsync = future1.thenCombineAsync(future2, (result1, result2) -> {
            System.out.println("任务5启动。。。结果1：" + result1 + "。。。结果2：" + result2);
            return result2 + "-->" + result1;
        }, service1Executor);
        System.out.println("任务5结果" + thenCombineAsync.get());
    }

    /***
     * 两任务组合，一个任务完成就执行
     *
     *  objectCompletableFuture.runAfterEither() 其中一个执行完执行
     *  objectCompletableFuture.acceptEither() 其中一个执行完执行+获取返回值
     *  objectCompletableFuture.applyToEither() 其中一个执行完执行+获取返回值+有返回值
     *
     */
    @Test
    public void main7() throws ExecutionException, InterruptedException {
        CompletableFuture<Object> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            try {
                Thread.sleep(3000);
                System.out.println("任务1运行结果：" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return i;
        }, service1Executor);

        CompletableFuture<Object> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2线程：" + Thread.currentThread().getId());
            System.out.println("任务2运行结果：");
            return "hello";
        }, service1Executor);

        CompletableFuture<Void> runAfterEitherAsync = future1.runAfterEitherAsync(future2, () -> {
            System.out.println("任务3开始执行。。。");
        }, service1Executor);

        CompletableFuture<Void> acceptEitherAsync = future1.acceptEitherAsync(future2, result -> {
            System.out.println("任务4开始执行。。。结果：" + result);
        }, service1Executor);

        CompletableFuture<String> applyToEitherAsync = future1.applyToEitherAsync(future2, result -> {
            System.out.println("任务5开始执行。。。结果：" + result);
            return result.toString() + " world";
        }, service1Executor);
        System.out.println("任务5结果：" + applyToEitherAsync.get());

    }

    /**
     * 多任务组合
     * <p>
     * allOf 等待所有任务完成
     * anyOf 只要一个任务完成
     */
    @Test
    public void main8() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1");
            return "任务1";
        }, service1Executor);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("任务2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "任务2";
        }, service1Executor);
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务3");
            return "任务3";
        }, service1Executor);
      /*  CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2, future3);
        //等待所有任务完成
        //allOf.get();
        allOf.join();
        System.out.println("allOf" + future1.get() + "-------" + future2.get() + "-------" + future3.get());*/

        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(future1, future2, future3);
        System.out.println("anyOf--最先完成的是" + anyOf.get());
    }
}
