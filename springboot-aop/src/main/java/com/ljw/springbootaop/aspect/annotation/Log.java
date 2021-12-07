package com.ljw.springbootaop.aspect.annotation;


import com.ljw.springbootaop.common.enums.ActionType;
import com.ljw.springbootaop.common.enums.OperatorType;

import java.lang.annotation.*;

/**
 * @Description: 日志注解
 * @Author: jianweil
 * @date: 2021/12/6 14:22
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    String module() default "";

    /**
     * 动作类别
     */
    ActionType actionType() default ActionType.OTHER;

    /**
     * 操作人类别
     */
    OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    boolean saveParameter() default true;
}
