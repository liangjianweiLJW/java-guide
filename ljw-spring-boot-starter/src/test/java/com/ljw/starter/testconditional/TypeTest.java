package com.ljw.starter.testconditional;

import com.ljw.starter.testconditional.config.TypeConfig;
import com.ljw.starter.testconditional.service.TypeService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description: 测试@Conditional注解
 * @Author: jianweil
 * @date: 2021/12/30 11:15
 */
public class TypeTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TypeConfig.class);
        TypeService typeService = applicationContext.getBean(TypeService.class);
        System.out.println(applicationContext.getEnvironment().getProperty("os.name") + "系统下的列表命令为：" + typeService.showCmd());
    }
}
