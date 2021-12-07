package com.ljw.springbootaop.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * @Description: 测试aop顺序
 * @Author: jianweil
 * @date: 2021/12/7 20:39
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AopOrderControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * 测试正常的aop执行顺序:
     * <p>
     * 1. @Around
     * 2. @Before
     * 3. 我的业务方法
     * 4. @AfterReturning
     * 5. @After
     * 6. @Around
     */
    @Test
    public void testAopOrder() {
        ResponseEntity<String> response = testRestTemplate.exchange("/aopOrder", HttpMethod.GET, null, String.class);
        Assertions.assertEquals("执行成功", response.getBody());
    }

    /**
     * 测试正常的aop执行顺序：
     * 注意没有@Around结尾
     * 1. @Around
     * 2. @Before
     * 3. 我的业务方法
     * 4. @AfterThrowing
     * 5. @After
     */
    @Test
    public void testAopOrderError() {
        ResponseEntity<String> response = testRestTemplate.exchange("/aopOrderError", HttpMethod.GET, null, String.class);
    }


}
