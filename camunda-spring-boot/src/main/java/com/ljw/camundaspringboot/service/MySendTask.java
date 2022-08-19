package com.ljw.camundaspringboot.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * @Description: 网关只能一个true 一个 false出口，有多个相同的只挑一个往下走
 * @Author: jianweil
 * @date: 2022/8/19 13:59
 */
public class MySendTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("发送任务 - send TASK - 回调");
        execution.getProcessEngineServices()
                .getRuntimeService()
                .createMessageCorrelation("myMessage")
                .processInstanceBusinessKey("myMessageBusinessKey")
                .correlate();
    }
}
