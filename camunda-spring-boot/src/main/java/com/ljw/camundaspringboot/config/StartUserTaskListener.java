package com.ljw.camundaspringboot.config;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 用户任务创建监听器(动态指派任务处理人)
 * <p>
 * <bpmn:userTask id="Activity_1fjgovd" name="审批人" camunda:assignee="xxx">
 * <bpmn:extensionElements>
 * <camunda:taskListener delegateExpression="${startUserTaskListener}" event="start">
 * <camunda:field name="users">
 * <camunda:string>11</camunda:string>
 * </camunda:field>
 * <camunda:field name="type">
 * <camunda:string>1</camunda:string>
 * </camunda:field
 * <camunda:field name="roles">
 * <camunda:string>1</camunda:string>
 * </camunda:field>
 * </camunda:executionListener>
 * <camunda:taskListener delegateExpression="${completeUserTaskListener}" event="complete"/>
 * </bpmn:extensionElements>
 * <bpmn:incoming>Flow_0hpg8dd</bpmn:incoming>
 * <bpmn:outgoing>Flow_0lbc6ui</bpmn:outgoing>
 * </bpmn:userTask>
 *
 * @author zhongwm
 * @date 2022/8/3
 */
@Component
public class StartUserTaskListener implements TaskListener, Serializable {

    @Resource
    HistoryService historyService;


    /**
     * 类型(1:指定人员，2:角色，3:主管，4：发起人自己)
     */
    private Expression type;

    /**
     * 用户ls
     */
    private Expression users;

    /**
     * 角色ls
     */
    private Expression roles;

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("监听器");
        DelegateExecution execution = delegateTask.getExecution();
    }


}
