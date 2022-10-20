package com.ljw.camundaspringboot.config;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.el.FixedValue;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 网关监听器
 * @author zhongwm
 * @date 2022/8/3
 */
@Component
public class GatewayListener implements ExecutionListener {

    @Resource
    RepositoryService repositoryService;
    @Resource
    HistoryService historyService;



    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String eventName = execution.getEventName();
        execution.setVariable("aaa",true);
        execution.setVariable("bbb",true);
        switch (eventName) {
            case EVENTNAME_START:
                System.out.println("gateway 流程启动:{}" + execution.getProcessInstanceId());
                break;
            case EVENTNAME_END:
                System.out.println("gateway  流程结束:{}" + execution.getProcessInstanceId());
                break;
            default:
                System.out.println("gateway  take:{}" + execution.getProcessInstanceId());
                break;
        }

    }
}
