---
theme: qklhk-chocolate
highlight: atom-one-dark
---

# 本章源码下载
[本章源码下载已分享github](https://github.com/liangjianweiLJW/java-guide/tree/master/springboot-timer)

# 1. Timer+TimerTask

- 这是jdk自带的java.util.Timer类，这个类允许你调度一个java.util.TimerTask任务。
- 使用这种方式可以让你的程序按照某一个频度执行，但不能在指定时间运行,一般用的较少。

```java
/**
 * @Description: 1. Timer+TimerTask：（jdk自带）
 * 这是java自带的java.util.Timer类，这个类允许你调度一个java.util.TimerTask任务。
 * 使用这种方式可以让你的程序按照某一个频度执行，但不能在指定时间运行。一般用的较少。
 * @Author: jianweil
 * @date: 2021/12/14 13:36
 */
public class TimerTest {
    public static void main(String[] args) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task  run:" + new Date());
            }
        };

        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task2  run:" + new Date());
                //多线程并行处理定时任务时，Timer运行多个TimeTask时，只要其中之一没有捕获抛出的异常，其它任务便会自动终止运行，使用ScheduledExecutorService则没有这个问题。
                int i = 1/0;
            }
        };
        //idea会提示：使用ScheduledExecutorService代替Timer吧
        Timer timer = new Timer();
        System.out.println("begin:" + new Date());
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。这里是延迟5秒开始执行，之后每3秒执行一次
        timer.schedule(timerTask, 5000, 3000);
        timer.schedule(timerTask2, 5000, 3000);
    }


}
```
- 多线程并行处理定时任务时，Timer运行多个TimeTask时，只要其中之一没有捕获抛出的异常，其它任务便会自动终止运行，使用ScheduledExecutorService则没有这个问题。

# 2. ScheduledExecutorService
- ScheduledExecutorService也是jdk自带的定时类，可以替代Timer

```java
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
```

# 3. Spring Task
- spring提供的类，可引入依赖：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
```
- 开启定时任务：@EnableScheduling
- 使用：在相应的任务方法前加上注解@Scheduled即可

## 3.1 单线程串行执行-@Scheduled
- @Scheduled注解默认使**同一个线程**中串行执行，如果只有一个定时任务，这样做肯定没问题，当定时任务增多，如果一个任务卡死，会导致其他任务也无法执行。
- 业务测试：
```java
@Component
@EnableScheduling
public class SpringTaskTest {
    @Scheduled(cron = "0/5 * * * * *")
    public void run() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "=====>>>>>使用cron  {}" + (System.currentTimeMillis() / 1000));
    }
} 
```
## 3.2 多线程并发运行-@Scheduled+配置定时器的程池（推荐）
- 解决单线程串行执行任务的问题，需要配置定时器的程池，推荐这种方法
- 配置并注入一个TaskScheduler类bean即可

- 配置定时器的线程池类如下：
```java
package com.ljw.springboottimer.springtask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @Description: 解决单线程串行执行 方式2:@Scheduled+配置定时器的线程池
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
```
- 业务测试
```java
@Component
@EnableScheduling
public class SpringTaskTest {

    @Scheduled(cron = "0/5 * * * * *")
    public void run() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "=====>>>>>使用cron  {}" + (System.currentTimeMillis() / 1000));
    }
    
    @Scheduled(fixedRate = 5000)
    public void run1() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "=====>>>>>使用fixedRate  {}" + (System.currentTimeMillis() / 1000));
    }

} 
```

## 3.3 多线程并发执行-@Scheduled+@Async+配置异步线程池
- 解决单线程串行执行任务的问题，也可以结合异步注解@Async实现，但这种方法并不推荐，需要两个注解，代码编写的工作量大
- **还可以解决fixedRate在遇到某些执行任务时间超过配置的时间隔，下次任务时间到了还要等待上次任务执行完成的情况，这是3.2不能解决的。**
- 配置异步线程池类如下：
```java
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
```
- 业务测试需要加上@Async注解
```java
@Component
@EnableScheduling
public class SpringTaskTest {

    @Scheduled(cron = "0/5 * * * * *")
    @Async
    public void run() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "=====>>>>>使用cron  {}" + (System.currentTimeMillis() / 1000));
    }
    
    @Scheduled(fixedRate = 5000)
    @Async
    public void run1() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "=====>>>>>使用fixedRate  {}" + (System.currentTimeMillis() / 1000));
    }

} 
```

- 如果同时配置了3.2配置定时器的程池和3.3配置异步线程池，并且注解使用了@Scheduled+@Async，则定时任务使用的线程池为：配置异步线程池

## @Scheduled参数解析：
- cron：通过cron表达式来配置任务执行时间（默认是fixedDelay）
- initialDelay ：定义该任务延迟多少时间才开始第一次执行
- fixedRate：定义一个按一定频率执行的定时任务。**fixedRate 每次任务结束后会从任务编排表中找下一次该执行的任务，判断是否到时机执行，fixedRate的任务某次执行时间再长也不会造成两次任务实例同时执行，也要等到上次任务完成，判断是否到时机执行，到就立即执行，与线程池无关，除非用了@Async注解，使方法异步，即是使用3.3步骤的配置。（3.2是配置线程池，达不到效果）**
- fixedDelay：定义一个按一定频率执行的定时任务。**fixedDelay总是在前一次任务完成后，延时固定时间长度然后再执行下一次任务**

# 4. Quartz
在开发Quartz相关应用时，只要定义了Job（任务），JobDetail（任务描述），Trigger（触发器）和Scheduler（调度器），即可实现一个定时调度能力。

如果SpringBoot版本是2.0.0以后的，则在spring-boot-starter中已经包含了quart的依赖，则可以直接引入依赖：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-quartz</artifactId>
</dependency>
```
## 4.1. 创建任务类
- 方式1：实现Job类的execute方法即可实现一个任务（推荐）
- 任务1如下：
```java
package com.ljw.springboottimer.quartz.do1;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @Description: 我的定时任务-方法1
 * @Author: jianweil
 * @date: 2021/12/14 16:06
 */
public class MyTaskService1 implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(Thread.currentThread().getName() + "------ Job ------" + new Date());
    }
}
```
- 方式2：继承QuartzJobBean类重写方法即可实现一个任务
- 任务2如下：
```java
package com.ljw.springboottimer.quartz.do1;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @Description: 我的定时任务-方法2
 * @Author: jianweil
 * @date: 2021/12/14 16:06
 */
public class MyTaskService2 extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println(Thread.currentThread().getName() + "---QuartzJobBean-----" + new Date());
    }
}
```

## 4.2. 配置任务描述和触发器

- 配置类要分别要为每个任务声明两个bean
    - 1.JobDetail（任务描述）
    - 2.Trigger（触发器）
- 配置调度器信息使用SimpleScheduleBuilder或者CronScheduleBuilder

```java
package com.ljw.springboottimer.quartz.do1;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @Description: 每个任务都要两步配置
 * 1.配置任务描述JobDetail 2. 配置触发器Trigger
 * @Author: jianweil
 * @date: 2021/12/14 16:08
 */
@Configuration
public class QuartzConfig {

    /**
     * 创建任务1的 JobDetail1
     *
     * @return
     */
    @Bean
    public JobDetail teatQuartzDetail1() {
        return JobBuilder.newJob(MyTaskService1.class)
                //job的描述
                .withDescription("this is a job1")
                //job 的name和group
                .withIdentity("myTrigger1", "myTriggerGroup1")
                .storeDurably().build();
    }

    /**
     * 创建任务2的 JobDetail2
     *
     * @return
     */
    @Bean
    public JobDetail teatQuartzDetail2() {
        return JobBuilder.newJob(MyTaskService2.class)
                //job的描述
                .withDescription("this is a job2")
                //job 的name和group
                .withIdentity("myTrigger2", "myTriggerGroup2")
                .storeDurably().build();
    }

    /**
     * 创建任务1的 Trigger1
     *
     * @return
     */
    @Bean
    public Trigger testQuartzTrigger1() {
        //使用SimpleScheduleBuilder或者CronScheduleBuilder
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                //设置时间周期单位秒
                .withIntervalInSeconds(10)
                .repeatForever();

        //两秒执行一次，Quartz表达式，支持各种牛逼表达式
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/3 * * * * ?");
        //任务运行的时间，SimpleSchedle类型触发器有效，3秒后启动任务
        long time = System.currentTimeMillis() + 3 * 1000L;
        Date statTime = new Date(time);


        return TriggerBuilder.newTrigger()
                .withDescription("")
                .forJob(teatQuartzDetail1())
                .withIdentity("myTrigger1", "myTriggerGroup1")
                //默认当前时间启动
                .startAt(statTime)
                .withSchedule(cronScheduleBuilder)
                //.withSchedule(scheduleBuilder)
                .build();

    }

    /**
     * 创建任务2的 Trigger2
     *
     * @return
     */
    @Bean
    public Trigger testQuartzTrigger2() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                //设置时间周期单位秒
                .withIntervalInSeconds(10)
                .repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(teatQuartzDetail2())
                .withIdentity("myTrigger2", "myTriggerGroup2")
                .withSchedule(scheduleBuilder)
                .build();
    }

}
```

- 👍🏻：有收获的，点赞鼓励！
- ❤️：收藏文章，方便回看！
- 💬：评论交流，互相进步！
