package com.ljw.validate;

import com.ljw.annotation.EnumValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2022/3/1 14:12
 */
public class EnumValueConstraintValidator implements ConstraintValidator<EnumValue, Object> {

    private Class<? extends Enum> enumClass;
    private static final String METHOD_NAME = "getValue";

    /**
     * 这个方法做一些初始化校验
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(EnumValue constraintAnnotation) {
        enumClass = constraintAnnotation.value();
        try {
            // 先判断该enum是否实现了getValue方法
            enumClass.getDeclaredMethod(METHOD_NAME);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("the enum class has not getValue method", e);
        }
    }

    /**
     * 这个方法写具体的校验逻辑：校验数字是否属于指定枚举类型的范围
     *
     * @param value
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        // 如果为空返回 true , 判空用 @NotNull 等专用注解
        if (Objects.isNull(value)) {
            return true;
        }
        try {
            Enum[] enumConstants = enumClass.getEnumConstants();
            if (enumConstants == null) {
                // 如果不是枚举类型，返回 enumConstants = null
                return true;
            }
            for (Enum e : enumConstants) {
                Method declaredMethod = e.getClass().getDeclaredMethod(METHOD_NAME);
                Object obj = declaredMethod.invoke(e);
                if (Objects.equals(obj, value)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
