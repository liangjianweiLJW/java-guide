package com.ljw.camundaspringboot.juece;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * @Description: 审批拒绝委托类
 * @Author: jianweil
 * @date: 2022/8/19 16:06
 */
public class RejectDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("审批【金额：" + execution.getVariables().get("amount")
                + ", 角色：" + execution.getVariables().get("role")
                + ", 资金方向：" + execution.getVariables().get("userFor") + "】已被拒绝。");
    }
}
