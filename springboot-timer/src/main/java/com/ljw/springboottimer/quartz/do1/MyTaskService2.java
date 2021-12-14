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
