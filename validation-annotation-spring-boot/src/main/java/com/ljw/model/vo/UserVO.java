package com.ljw.model.vo;

import com.ljw.annotation.EnumValue;
import com.ljw.annotation.ListValue;
import com.ljw.annotation.Mobile;
import com.ljw.enums.Gender;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @Description: 用户VO
 * @Author: jianweil
 * @date: 2022/3/1 10:03
 */
@Data
public class UserVO {

    @NotBlank(message = "姓名不能为空")
    private String name;

    @Min(value = 18, message = "年龄不能小于18")
    private int age;

    @Mobile(message = "电话格式不对啊！！！")
    private String mobile;
    /**
     * 列举性别枚举值: 1女 2男
     */
    @ListValue(value = {"1", "2"}, message = "性别参数不对", required = false)
    private Integer gender;

    @EnumValue(value = Gender.class, message = "不在系统支持的枚举范围内",required = false)
    private Integer genderEnum;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

}
