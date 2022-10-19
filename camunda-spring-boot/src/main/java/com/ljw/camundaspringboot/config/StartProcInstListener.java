package com.ljw.camundaspringboot.config;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 流程启动监听器
 * <p>
 * <bpmn:extensionElements>
 * <camunda:executionListener delegateExpression="${endProcInstListener}" event="end" />
 * <camunda:executionListener delegateExpression="${startProcInstListener}" event="start" />
 * </bpmn:extensionElements>
 *
 * @author zhongwm
 * @date 2022/8/3
 */
@Component
public class StartProcInstListener implements ExecutionListener {

    @Resource
    RepositoryService repositoryService;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String eventName = execution.getEventName();
        switch (eventName) {
            case EVENTNAME_START:
                System.out.println("流程启动:{}" + execution.getProcessInstanceId());
                ProcessDefinition definition = repositoryService.getProcessDefinition(execution.getProcessDefinitionId());
                String category = definition.getCategory();

                break;
            case EVENTNAME_END:
                System.out.println("流程结束:{}" + execution.getProcessInstanceId());
                break;
            default:
                break;
        }

    }
}
