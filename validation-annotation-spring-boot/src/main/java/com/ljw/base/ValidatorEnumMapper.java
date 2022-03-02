package com.ljw.base;

/**
 * @Description: 枚举校验映射接口
 * @Author: jianweil
 * @date: 2022/3/1 15:24
 */
public interface ValidatorEnumMapper<T> {

    /**
     * 名称和下面方法名称相同
     */
    public static final String METHOD_NAME = "getValue";

    /**
     * 注解约束验证器调用这个方法获取枚举的值
     **/
    public T getValue();
}
