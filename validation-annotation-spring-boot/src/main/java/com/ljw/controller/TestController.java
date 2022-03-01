package com.ljw.controller;

import com.ljw.model.vo.UserVO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @Description: 测试控制器
 * @Author: jianweil
 * @date: 2022/3/1 10:02
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 捕获数据绑定结果，如果数据格式报错是不会触发全局的异常捕获，这里BindingResult已经捕获了
     *
     * @param vo
     * @param rs
     * @return
     */
    @PostMapping("/add")
    public Object add(@RequestBody @Valid UserVO vo, BindingResult rs) {
        //打印错误
        StringBuilder msg = new StringBuilder();
        if (rs.hasErrors()) {
            List<FieldError> fieldErrors = rs.getFieldErrors();
            fieldErrors.forEach(item -> msg.append(item.getDefaultMessage()).append(";"));
            return msg.toString();
        }
        return vo;
    }

    /**
     * 推荐：请求数据错误由全局异常处理器捕获，这里关注业务
     *
     * @param vo
     * @return
     */
    @PostMapping("/add2")
    public Object add2(@RequestBody @Valid UserVO vo) {
        System.out.println("检验成功...");
        return vo;
    }

}
