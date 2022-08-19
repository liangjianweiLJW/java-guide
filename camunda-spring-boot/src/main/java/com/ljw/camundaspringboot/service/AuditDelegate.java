package com.ljw.camundaspringboot.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2022/8/19 11:23
 */
public class AuditDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("业务任务 - SERVICE TASK - 回调");
    }
}
