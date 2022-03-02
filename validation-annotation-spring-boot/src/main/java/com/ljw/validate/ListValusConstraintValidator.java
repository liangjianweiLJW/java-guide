package com.ljw.validate;

import com.ljw.annotation.ListValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @Description: Interger list集合校验器
 * @Author: jianweil
 * @date: 2021/6/8 11:20
 */
public class ListValusConstraintValidator implements ConstraintValidator<ListValue, Object> {

    private Set<Object> valus = new HashSet<>();
    boolean required = true;

    @Override
    public void initialize(ListValue constraintAnnotation) {
        try {
            String[] value = constraintAnnotation.value();
            for (String i : value) {
                valus.add(i);
            }
            required = constraintAnnotation.required();
        } catch (Exception e) {
            throw new RuntimeException("参数校验转换出错", e);
        }
    }

    /**
     * 是否校验成功
     *
     * @param value   校验值
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (required) {
            if (Objects.isNull(value)) {
                //value为null
                return false;
            }
            return valus.contains(value.toString());
        } else {
            //value可以为null
            if (value == null) {
                return true;
            } else {
                //如果value不为null，校验是否是列举的值
                return valus.contains(value.toString());
            }
        }
    }
}
