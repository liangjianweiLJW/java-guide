package com.ljw.camundaspringboot.juece;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * @Description:  审批同意委托类
 * @Author: jianweil
 * @date: 2022/8/19 16:06
 */
public class AgreeDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("审批【金额："
                + execution.getVariablesLocal().get("amount") + "】已被同意。");
    }
}
