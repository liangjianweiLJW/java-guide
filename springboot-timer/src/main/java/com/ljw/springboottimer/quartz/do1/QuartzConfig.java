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
