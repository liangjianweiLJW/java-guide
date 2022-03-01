package com.ljw.validate;

import com.ljw.annotation.Mobile;
import com.ljw.utils.MobileUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2022/3/1 13:12
 */
public class MobileConstraintValidator implements ConstraintValidator<Mobile, String> {
    private boolean required = false;

    @Override
    public void initialize(Mobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required) {
            return MobileUtil.isMobile(value);
        } else {
            if (StringUtils.isEmpty(value)) {
                return true;
            } else {
                return MobileUtil.isMobile(value);
            }
        }
    }
}