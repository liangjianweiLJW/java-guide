package com.ljw.enums;

import com.ljw.base.ValidatorEnumMapper;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2022/3/1 14:09
 */
public enum Gender implements ValidatorEnumMapper<Integer> {

    male(0),
    female(1);

    private int value;

    Gender(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
