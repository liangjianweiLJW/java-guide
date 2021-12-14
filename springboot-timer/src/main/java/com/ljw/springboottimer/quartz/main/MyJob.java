package com.ljw.springboottimer.quartz.main;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/14 16:18
 */
public class MyJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("quartz MyJob date:" + System.currentTimeMillis()/1000);
    }
}
