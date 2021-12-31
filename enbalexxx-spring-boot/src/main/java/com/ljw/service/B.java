package com.ljw.service;

import org.springframework.beans.factory.InitializingBean;

/**
 * @Description: @Configuration注入
 * @Author: jianweil
 * @date: 2021/12/31 11:50
 */
public class B implements InitializingBean {
    public B() {
        System.out.println("======B实例化======");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行B的业务方法");
    }
}
