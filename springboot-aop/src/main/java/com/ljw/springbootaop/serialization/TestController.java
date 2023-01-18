package com.ljw.springbootaop.serialization;

import com.ljw.springbootaop.aspect.annotation.AopOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2022/11/18 13:07
 */
@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {

    @GetMapping("/testSerialization")
    public TestVo testSerialization() {
        TestVo testVo = new TestVo(1L,"TestVo");
        //testVo.setId(1L);
        //testVo.setValue("我是setValue");
        //testVo.setName("我是setName");
        return testVo;
    }
}
