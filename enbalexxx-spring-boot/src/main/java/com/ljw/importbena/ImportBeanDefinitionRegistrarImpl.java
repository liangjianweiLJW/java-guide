package com.ljw.importbena;

import com.ljw.annotation.EnableDefinedBean;
import com.ljw.service.D;
import com.ljw.service.Non;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

public class ImportBeanDefinitionRegistrarImpl implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //在这里可以拿到所有注解的信息，可以根据不同注解的和注解的属性来返回不同的class,从而达到开启不同功能的目的
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(
                        EnableDefinedBean.class.getName()));

        //根据注解配置参数值是否注入Non.class
        boolean isBeanNon = annotationAttributes.getBoolean("isBeanNon");
        if (isBeanNon) {
            Class nonbeanClass = Non.class;
            RootBeanDefinition nonbeanDefinition = new RootBeanDefinition(nonbeanClass);
            String beanName = StringUtils.uncapitalize(nonbeanClass.getSimpleName());
            registry.registerBeanDefinition(beanName, nonbeanDefinition);
        }
        //默认注入D.class
        Class beanClass = D.class;
        RootBeanDefinition beanDefinition = new RootBeanDefinition(beanClass);
        String beanName = StringUtils.uncapitalize(beanClass.getSimpleName());
        //在这里可以拿到所有注解的信息，可以根据不同注解来返回不同的class,从而达到开启不同功能的目的
        //通过这种方式可以自定义beanName
        registry.registerBeanDefinition(beanName, beanDefinition);
    }
}
