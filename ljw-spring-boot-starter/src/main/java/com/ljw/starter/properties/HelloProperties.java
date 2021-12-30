package com.ljw.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description: 配置信息：@ConfigurationProperties可以定义一个配置信息类，映射配置文件
 * @Author: jianweil
 * @date: 2021/12/30 11:53
 */
@ConfigurationProperties(prefix = "ljw.config")
public class HelloProperties {

    private String name = "hello 默认值！";

    private int age = 8;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
