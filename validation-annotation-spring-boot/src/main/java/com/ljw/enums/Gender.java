package com.ljw.enums;

import com.ljw.base.ValidatorEnumMapper;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2022/3/1 14:09
 */
public enum Gender implements ValidatorEnumMapper<Integer> {

    male(1, "男"),
    female(2, "女");

    private int value;
    private String type;

    Gender(int value, String type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
