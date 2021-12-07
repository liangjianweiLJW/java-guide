package com.ljw.springbootaop.controller;

import com.ljw.springbootaop.aspect.annotation.AopOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/7 20:37
 */
@RestController
@Slf4j
public class AopOrderController {

    @GetMapping("/aopOrder")
    @AopOrder(module = "测试aop顺序")
    public String aopOrder() throws InterruptedException {
        log.info("我的业务方法");
        Thread.sleep(1000);
        return "执行成功";
    }


    @GetMapping("/aopOrderError")
    @AopOrder(module = "测试aop顺序")
    public String aopOrderError() {
        log.info("我的业务方法");
        int i = 1 / 0;
        return "执行失败";
    }
}
