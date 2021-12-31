package com.ljw.properties;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/31 14:10
 */
public class Address {
    private String tel;
    private String name;

    @Override
    public String toString() {
        return "Address{" +
                "tel='" + tel + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
