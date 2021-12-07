package com.ljw.springbootaop.controller;

import cn.hutool.core.util.IdUtil;
import com.ljw.springbootaop.model.dto.LoginUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;


/**
 * @Description: 测试日志
 * @Author: jianweil
 * @date: 2021/12/6 16:02
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DoSomethingControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String param = "test";

    /**
     * 测试restful get
     */
    @Test
    public void testGet() {
        String url = "/do?param={param}";
        Map<String, String> params = new HashMap<>();
        params.put("param", param);
        String response = testRestTemplate.getForObject(url, String.class, params);
        Assertions.assertEquals(param, response);
    }

    /**
     * 测试get
     */
    @Test
    public void testGet1() {
        ResponseEntity<String> response = testRestTemplate.exchange("/do/" + param, HttpMethod.GET, null, String.class);
        Assertions.assertEquals(param, response.getBody());
    }


    /**
     * 测试post请求
     */
    @Test
    public void testPost() {
        String uuid = IdUtil.randomUUID();
        // 要发送的数据对象
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(uuid);
        loginUser.setToken(uuid);
        // 发送post请求，并输出结果
        LoginUser result = testRestTemplate.postForObject("/do", loginUser, LoginUser.class);
        Assertions.assertEquals(uuid, result.getToken());
    }


    /**
     * 测试put
     */
    @Test
    public void testPut() {
        //创建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String uuid = IdUtil.randomUUID();
        // 要发送的数据对象
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(uuid);
        loginUser.setToken(uuid);
        HttpEntity<LoginUser> entity = new HttpEntity<>(loginUser, headers);
        ResponseEntity<LoginUser> response = testRestTemplate.exchange("/do", HttpMethod.PUT, entity, LoginUser.class);
        Assertions.assertEquals(uuid, response.getBody().getToken());
    }

    /**
     * 测试delete
     */
    @Test
    public void testDelete() {
        String uuid = IdUtil.randomUUID();
        ResponseEntity<String> response = testRestTemplate.exchange("/do/" + uuid, HttpMethod.DELETE, null, String.class);
        Assertions.assertEquals(uuid, response.getBody());
    }
}
