package com.ljw.springbootthreadpool.spring.completablefuture;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description: 线程池属性
 * @Author: jianweil
 * @date: 2021/12/9 10:44
 */
@ConfigurationProperties(prefix = "service1.thread")
@Data
public class ThreadPoolConfigProperties {

    private Integer coreSize;

    private Integer maxSize;

    private Integer keepAliveTime;


}
