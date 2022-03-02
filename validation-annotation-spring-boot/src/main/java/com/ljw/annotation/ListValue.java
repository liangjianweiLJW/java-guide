package com.ljw.annotation;


import com.ljw.validate.ListValusConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Description: Interger list集合校验
 * @Author: jianweil
 * @date: 2021/6/8 11:02
 */

@Documented
@Constraint(validatedBy = {ListValusConstraintValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface ListValue {

    String message() default "参数不匹配!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 需要校验int String数组
     *
     * @return
     */
    String[] value();

    /**
     * true 必传
     * false 非必传
     * @return
     */
    boolean required() default true;
}
