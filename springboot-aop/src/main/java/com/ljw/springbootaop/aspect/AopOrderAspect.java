package com.ljw.springbootaop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Description: aop各种注解的执行顺序
 * @Author: jianweil
 * @date: 2021/12/6 17:32
 */
@Aspect
@Component
@Slf4j
public class AopOrderAspect {

    long beginTime;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.ljw.springbootaop.aspect.annotation.AopOrder)")
    public void Pointcut() {
    }


    @Before("Pointcut()")
    public void beforeMethod() {
        log.info("前置通知@Before");
        beginTime = System.currentTimeMillis();
    }

    @AfterReturning(value = "Pointcut()", returning = "result")
    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
        log.info("返回通知@AfterReturning,result :{}", result);
    }

    @AfterThrowing(value = "Pointcut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        log.info("异常通知@AfterThrowing  joinPoint:{},错误原因 :{}", joinPoint, e.getMessage());
    }

    @After(value = "Pointcut()")
    public void afterMethod(JoinPoint joinPoint) {
        //joinPoint: 当前的连接点，即执行的切入点
        log.info("后置通知@After,joinPoint:{}", joinPoint);
    }

    @Around("Pointcut()")
    public Object Around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("环绕通知@Around前");
        // 执行方法
        Object object = proceedingJoinPoint.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        log.info("环绕通知@Around后,返回值: {},执行时间：{}", object, time);
        return object;
    }

}
