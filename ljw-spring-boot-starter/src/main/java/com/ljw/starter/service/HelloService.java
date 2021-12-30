package com.ljw.starter.service;

/**
 * @Description: 服务类
 * @Author: jianweil
 * @date: 2021/12/30 11:57
 */
public class HelloService {

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String hello() {
        return "HelloService{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
