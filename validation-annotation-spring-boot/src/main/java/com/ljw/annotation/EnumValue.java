package com.ljw.annotation;

import com.ljw.validate.EnumValueConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2022/3/1 14:12
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumValueConstraintValidator.class})
public @interface EnumValue {

    // 默认错误消息
    String message() default "the integer is not one of the enum values";

    // 约束注解在验证时所属的组别
    Class<?>[] groups() default {};

    // 约束注解的有效负载
    Class<? extends Payload>[] payload() default {};

    /**
     * true 必传
     * false 非必传
     *
     * @return
     */
    boolean required() default true;

    /**
     * 需要校验枚举的值
     *
     * @return
     */
    Class<? extends Enum> value();

}
