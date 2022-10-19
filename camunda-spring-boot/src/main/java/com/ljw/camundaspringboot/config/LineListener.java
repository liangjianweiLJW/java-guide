package com.ljw.camundaspringboot.config;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.el.FixedValue;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 流程结束监听器
 * @author zhongwm
 * @date 2022/8/3
 */
@Component
public class LineListener implements ExecutionListener {

    @Resource
    RepositoryService repositoryService;
    @Resource
    HistoryService historyService;

    /**
     * 这里是每个自定义属性的字段名
     */
    private FixedValue my1;

    private FixedValue my2;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String eventName = execution.getEventName();
        switch (eventName) {
            case EVENTNAME_START:
                System.out.println("line流程启动:{}" + execution.getProcessInstanceId());
                break;
            case EVENTNAME_END:
                System.out.println("line流程结束:{}" + execution.getProcessInstanceId());
                break;
            default:
                break;
        }

    }
}
