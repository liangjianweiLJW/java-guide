package com.ljw.annotation;

import com.ljw.importbena.ImportBeanDefinitionRegistrarImpl;
import com.ljw.importbena.ImportSelectorImpl;
import com.ljw.service.A;
import com.sun.media.jfxmedia.track.Track;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:
 * @Author: jianweil
 * @date: 2021/12/31 11:33
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
//导入属性配置文件
@PropertySource(value = "classpath:user.properties", encoding ="UTF-8" )
//导入一个bean定义资源
@ImportResource(locations = "classpath:beans.xml")
//导入一些bean类
@Import({A.class, ImportSelectorImpl.class, ImportBeanDefinitionRegistrarImpl.class})
public @interface EnableDefinedBean {

    boolean isBeanNon() default false;
}
