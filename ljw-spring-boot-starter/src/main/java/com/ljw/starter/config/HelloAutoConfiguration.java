package com.ljw.starter.config;

import com.ljw.starter.properties.HelloProperties;
import com.ljw.starter.service.HelloService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/30 11:58
 */
@Configuration(proxyBeanMethods = false)
// 当存在某个类时，此自动配置类才会生效
@ConditionalOnClass(value = {HelloService.class})
// 导入我们自定义的配置类,供当前类使用
@EnableConfigurationProperties(value = HelloProperties.class)
// 只有非web应用程序时此自动配置类才会生效
@ConditionalOnWebApplication
//判断ljw.config.flag的值是否为“true”， matchIfMissing = true：没有该配置属性时也会正常加载
@ConditionalOnProperty(prefix = "ljw.config", name = "flag", havingValue = "true", matchIfMissing = true)
public class HelloAutoConfiguration {

    /**
     * @param helloProperties 直接方法签名入参注入HelloProperties,也可以使用属性注入
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(HelloService.class)
    //@ConditionalOnProperty(prefix = "ljw.config", name = "flag", havingValue = "true", matchIfMissing = true)
    public HelloService helloService(HelloProperties helloProperties) {
        HelloService helloService = new HelloService();
        helloService.setName(helloProperties.getName());
        helloService.setAge(helloProperties.getAge());
        return helloService;
    }

}
