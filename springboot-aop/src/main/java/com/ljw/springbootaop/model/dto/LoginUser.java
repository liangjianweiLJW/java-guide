package com.ljw.springbootaop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 登录用户信息
 * @Author: jianweil
 * @date: 2021/12/6 15:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 用户名
     */
    private String username;

}
