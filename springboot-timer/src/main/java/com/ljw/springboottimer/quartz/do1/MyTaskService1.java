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
