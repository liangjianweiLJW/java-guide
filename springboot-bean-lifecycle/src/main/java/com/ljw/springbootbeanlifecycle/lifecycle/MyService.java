package com.ljw.springbootbeanlifecycle.lifecycle;

import com.ljw.springbootbeanlifecycle.lifecycle.service.Animal;
import com.ljw.springbootbeanlifecycle.lifecycle.service.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Description: bean生命周期测试：这些接口只针对当前bean
 * https://blog.csdn.net/Jintao_Ma/article/details/52400730?utm_medium=distribute.pc_aggpage_search_result.none-task-blog-2~all~first_rank_v2~rank_v25-2-52400730.nonecase
 * @Author: jianweil
 * @date: 2021/12/8 9:46
 */
@Slf4j
public class MyService implements Person, BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean {

    private Animal animal = null;

    private ApplicationContext applicationContext;

    /**
     *接口规定方法
     */
    @Override
    public void service() {
        this.animal.use();
    }

    public MyService() {
        log.info("{}:构造方法",this.getClass().getSimpleName());
    }
    /**
     *接口规定方法：注入依赖
     */
    @Override
    @Autowired
    @Qualifier("dog")
    public void setAnimal(Animal animal) {
        log.info("dog----依赖注入");
        this.animal = animal;
    }


    @Override
    public void setBeanName(String s) {
        log.info("调用【BeanNameAware】--setBeanName({})", s);

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("调用【BeanFactoryAware】--setBeanFactory");
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        log.info("调用【ApplicationContextAware】--setApplicationContext");

    }

    /**
     * 初始化1
     */
    @PostConstruct
    public void myInit() {
        log.info("注解@PostConstruct自定义初始化方法[myInit]");
    }

    /**
     * 初始化2
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("调用【InitializingBean】--afterPropertiesSet");

    }

    /**
     * 初始化3
     */
    public void initMethod() {
        log.info("注解@Bean自定义初始化方法[initMethod]");
    }

    /**
     * 销毁1
     */
    @PreDestroy
    public void myDestroy() {
        log.info("注解@PreDestroy自定义销毁方法[myDestroy]");
    }

    /**
     * 销毁2
     */
    @Override
    public void destroy() throws Exception {
        log.info("调用【DisposableBean】--destroy");
    }

    /**
     * 销毁3
     */
    public void destroyMethod() {
        log.info("注解@Bean自定义销毁方法[destroyMethod]");
    }
}
