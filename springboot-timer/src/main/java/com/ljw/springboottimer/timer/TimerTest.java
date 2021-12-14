package com.ljw.springboottimer.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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
