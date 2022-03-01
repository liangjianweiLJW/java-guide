package com.ljw.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * @Description: 全局异常处理器
 * @Author: jianweil
 * @date: 2022/3/1 10:37
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 接口参数数据格式错误异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException");
        StringBuilder msg = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach(item -> msg.append(item.getDefaultMessage()).append(";"));
        return msg.toString();
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handleException(Exception e) {
        System.out.println(e);
        return e.getMessage();
    }
}
