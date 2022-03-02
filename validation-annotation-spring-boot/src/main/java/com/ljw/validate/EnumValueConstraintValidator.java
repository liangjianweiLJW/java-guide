package com.ljw.validate;

import com.ljw.annotation.EnumValue;
import com.ljw.base.ValidatorEnumMapper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2022/3/1 14:12
 */
public class EnumValueConstraintValidator implements ConstraintValidator<EnumValue, Object> {

    private Set<Object> values = new HashSet<>();
    private Class<? extends Enum> enumClass;
    boolean required = true;

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
            enumClass.getDeclaredMethod(ValidatorEnumMapper.METHOD_NAME);
            for (Enum e : enumClass.getEnumConstants()) {
                Method declaredMethod = e.getClass().getDeclaredMethod(ValidatorEnumMapper.METHOD_NAME);
                Object obj = declaredMethod.invoke(e);
                values.add(obj);
            }
            required = constraintAnnotation.required();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("the enum class has not getValue method", e);
        } catch (Exception e) {
            e.printStackTrace();
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
        if (required) {
            if (Objects.isNull(value)) {
                return false;
            }
            return isPass(value);

        } else {
            //value可以为null
            if (Objects.isNull(value)) {
                return true;
            }
            return isPass(value);
        }
    }

    /**
     * value不为null时检验是否匹配
     *
     * @param value
     * @return
     */
    private boolean isPass(Object value) {
        //不为null
        Enum[] enumConstants = enumClass.getEnumConstants();
        if (enumConstants == null) {
            // 如果不是枚举类型，返回 enumConstants = null
            return true;
        }
        return values.contains(value);
    }
}
