package com.ljw.starter.testconditional.service.impl;

import com.ljw.starter.testconditional.service.TypeService;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/30 11:21
 */
public class LinuxServiceImpl implements TypeService {
    @Override
    public String showCmd() {
        return "ls";
    }
}
