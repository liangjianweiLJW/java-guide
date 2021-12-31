package com.ljw.service;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/31 13:49
 */
public class ImportSourceService {

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

    @Override
    public String toString() {
        return "ImportSourceService{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
