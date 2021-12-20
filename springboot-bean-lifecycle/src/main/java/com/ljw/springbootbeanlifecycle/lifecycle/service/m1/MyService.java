package com.ljw.springbootbeanlifecycle.lifecycle.service.m1;

import com.ljw.springbootbeanlifecycle.lifecycle.service.m2.Animal;
import com.ljw.springbootbeanlifecycle.lifecycle.service.m2.Person;
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
        System.out.println("2. [bean实例化]："+this.getClass().getSimpleName()+"----------构造方法");
    }
    /**
     *接口规定方法：注入依赖
     */
    @Override
    @Autowired
    @Qualifier("dog")
    public void setAnimal(Animal animal) {
        System.out.println("5. [bean属性赋值]：dog----依赖注入");
        this.animal = animal;
    }


    @Override
    public void setBeanName(String s) {
        System.out.println("6. 调用【BeanNameAware】--setBeanName:"+s);

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("7. 调用【BeanFactoryAware】--setBeanFactory");
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("8. 调用【ApplicationContextAware】--setApplicationContext");

    }

    /**
     * 初始化1
     */
    @PostConstruct
    public void myInit() {
        System.out.println("10. [初始化] 注解@PostConstruct自定义初始化方法[myInit]");
    }

    /**
     * 初始化2
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("11. [初始化] 接口InitializingBean方法[afterPropertiesSet]");

    }

    /**
     * 初始化3
     */
    public void initMethod() {
        System.out.println("12. [初始化] 注解@Bean自定义初始化方法[initMethod]");
    }

    /**
     * 销毁1
     */
    @PreDestroy
    public void myDestroy() {
        System.out.println("14. [销毁] 注解@PreDestroy自定义销毁方法[myDestroy]");
    }

    /**
     * 销毁2
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("15. [销毁] 接口DisposableBean方法[destroy]");
    }

    /**
     * 销毁3
     */
    public void destroyMethod() {
        System.out.println("16. [销毁] 注解@Bean自定义销毁方法[destroyMethod]");
    }
}
