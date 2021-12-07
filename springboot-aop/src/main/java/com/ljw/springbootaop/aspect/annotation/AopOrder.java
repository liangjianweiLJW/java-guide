package com.ljw.springbootaop.aspect.annotation;

/**
 * @Description: aop相关执行顺序
 * @Author: jianweil
 * @date: 2021/12/6 17:32
 */
public @interface AopOrder {
    //模块名
    String module() default "";
}
