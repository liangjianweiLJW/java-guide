package com.ljw.springbootaop.controller;

import com.ljw.springbootaop.aspect.annotation.Log;
import com.ljw.springbootaop.common.enums.ActionType;
import com.ljw.springbootaop.common.enums.OperatorType;
import com.ljw.springbootaop.model.dto.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 日志操作
 * @Author: jianweil
 * @date: 2021/12/6 15:49
 */
@RestController
@RequestMapping("/do")
@Slf4j
public class DoSomethingController {


    @Log(module = "查询操作", actionType = ActionType.QUERY, operatorType = OperatorType.MANAGE, saveParameter = false)
    @GetMapping
    public String getXxx(@RequestParam String param) {
        log.info("get请求，参数：{}", param);
        return param;
    }

    @Log(module = "查询操作", actionType = ActionType.QUERY, operatorType = OperatorType.MANAGE, saveParameter = true)
    @GetMapping("/{param}")
    public String getXxx1(@PathVariable String param) {
        log.info("get请求，参数：{}", param);
        return param;
    }

    @Log(module = "增加操作", actionType = ActionType.INSERT, operatorType = OperatorType.MOBILE, saveParameter = true)
    @PostMapping
    public LoginUser postXxx(@RequestBody LoginUser loginUser) {
        log.info("post请求，参数：{}", loginUser.toString());
        return loginUser;
    }

    @Log(module = "修改操作", actionType = ActionType.UPDATE, operatorType = OperatorType.MOBILE, saveParameter = true)
    @PutMapping
    public LoginUser updateXxx(@RequestBody LoginUser loginUser) {
        log.info("put请求，参数：{}", loginUser.toString());
        return loginUser;
    }

    @Log(module = "删除操作", actionType = ActionType.DELETE, operatorType = OperatorType.MANAGE, saveParameter = true)
    @DeleteMapping("/{param}")
    public String deleteXxx(@PathVariable String param) {
        log.info("delete请求，参数：{}", param);
        return param;
    }

}
