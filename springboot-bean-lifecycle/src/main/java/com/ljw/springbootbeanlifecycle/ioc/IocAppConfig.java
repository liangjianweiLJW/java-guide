
package com.ljw.springbootbeanlifecycle.ioc;

import com.ljw.springbootbeanlifecycle.lifecycle.config.BeanInitAndDestroyConfig;
import com.ljw.springbootbeanlifecycle.lifecycle.service.m2.impl.Cat;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @Description: lazyInit=true用到依赖时才会实例化
 * https://www.cnblogs.com/binghe001/p/13063046.html
 * @Author: jianweil
 * @date: 2021/12/8 11:53
 */
@Configuration
@ComponentScan(basePackages = {"com.ljw.springbootbeanlifecycle"}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {Cat.class, BeanInitAndDestroyConfig.class})}/*,lazyInit = true*/)
public class IocAppConfig {
}
