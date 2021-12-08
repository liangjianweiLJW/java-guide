---
theme: qklhk-chocolate
highlight: atom-one-dark
---

# 源码下载


# 接口
## BeanNameAware
- BeanNameAware接口是为了让自身Bean能够感知到，获取到自身在Spring容器中的id或name属性。

## BeanFactoryAware
- 让Bean获取配置他们的BeanFactory的引用，可以对工厂中的所有bean进行扩展。

## ApplicationContextAware
- 为了让Bean获取它所在的Spring容器，可以对整个容器进行扩展。
## InitializingBean
- 为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候都会执行该方法，可以进行一些属性配置等工作。

## DisposableBean
- 该接口的作用是在对象销毁时调用，可以做一些资源销毁操作。

# 注解

## @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
- @Bean声明一个bean,配合@Configuration注解使用
- initMethod：声明bean初始化时回调一个方法，该方法需要程序员编写
- destroyMethod：声明bean销毁时回调一个方法，该方法需要程序员编写

## @PostConstruct
- bean的一个基于注解的初始化方法

## @PreDestroy
- bean的一个基于注解的销毁方法


# 案例分析
## 声明一个bean
```java
@Configuration
public class BeanInitAndDestroyConfig {
    /**
     * @return 这里没有指定bean名字，默认是方法名
     */
    @Description("测试bean的生命周期")
    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public MyService myDefineBeanName() {//入参数可注入其他依赖
        return new MyService();
    }
}
```
- 声明一个名为：myDefineBeanName的bean
- initMethod：bean的初始化方法为：initMethod
- destroyMethod：bean的销毁方法为：destroyMethod


## Animal实现类
![image.png](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/689407091478424db98720703b2ba290~tplv-k3u1fbpfcp-watermark.image?)
- 这里只是想用来说明 @Qualifier注解能根据bean名称匹配。


## 我的服务类-只针对当前bean

```java
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

    @Override
    public void service() {
        this.animal.use();
    }

    public MyService() {
        log.info("{}:构造方法",this.getClass().getSimpleName());
    }

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
```
- 这里实现的接口只作用于当前bean（即是上面@bean定义的bean名为myDefineBeanName）生命周期



## 后置处理器-针对所有的bean
```java
/**
 * @Description: 后置bean的初始化器：所有的bean都会拦截执行
 * @Author: jianweil
 * @date: 2021/12/8 9:46
 *
 */
@Component
@Slf4j
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //这里过滤掉springboot自动配置的bean，只打印我们项目的bean情况
        if (bean.getClass().getName().contains("com.ljw.springbootbeanlifecycle")) {
            log.info("======================postProcessBeforeInitialization==============================");
            log.info("BeanPostProcessor调用postProcessBeforeInitialization方法：beanName为{}",beanName);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().getName().contains("com.ljw.springbootbeanlifecycle")) {
            log.info("======================postProcessAfterInitialization==============================");
            log.info("BeanPostProcessor调用postProcessAfterInitialization方法：beanName为{}",beanName);
        }
        return bean;
    }
}
```
- BeanPostProcessor后置处理器是作用于ioc容器中所有bean的生命周期。


## 输出结果和结果解读
- 已去掉无关的代码
- //标记为解读

```java
//1. springbootBeanLifecycleApplication为项目启动类，首先初始化bean进入ioc容器，我们配置了后置处理器，所有输入了以下内容
======================postProcessBeforeInitialization==============================
BeanPostProcessor调用postProcessBeforeInitialization方法：beanName为springbootBeanLifecycleApplication
======================postProcessAfterInitialization==============================
BeanPostProcessor调用postProcessAfterInitialization方法：beanName为springbootBeanLifecycleApplication

//2. spring自上而下扫描，扫到beanInitAndDestroyConfig为配置类，初始化进入ioc容器
======================postProcessBeforeInitialization==============================
 BeanPostProcessor调用postProcessBeforeInitialization方法：beanName为beanInitAndDestroyConfig
 ======================postProcessAfterInitialization==============================
BeanPostProcessor调用postProcessAfterInitialization方法：beanName为beanInitAndDestroyConfig

//3. spring自上而下扫描，扫到cat有@Component注解，初始化进入ioc容器
======================postProcessBeforeInitialization==============================
 BeanPostProcessor调用postProcessBeforeInitialization方法：beanName为cat
======================postProcessAfterInitialization==============================
 BeanPostProcessor调用postProcessAfterInitialization方法：beanName为cat
 
//4. spring自上而下扫描，扫到cat有@Component注解，先构造方法，再初始化进入ioc容器
 狗----------构造方法
//5. 再初始化进入ioc容器
======================postProcessBeforeInitialization==============================
 BeanPostProcessor调用postProcessBeforeInitialization方法：beanName为dog
======================postProcessAfterInitialization==============================
BeanPostProcessor调用postProcessAfterInitialization方法：beanName为dog


//6. 由于上面声明了一个MyService类型的bean名称为myDefineBeanName，先构造方法
MyService:构造方法
//7. 因为该bean有属性animal依赖需要注入，先注入dog，由于dog上面已经初始化放在ioc容器，直接拿
dog----依赖注入
//8.BeanNameAware接口，该bean私有的声明过程
调用【BeanNameAware】--setBeanName(myDefineBeanName)
//9.BeanFactoryAware接口，该bean私有的声明过程
调用【BeanFactoryAware】--setBeanFactory
//10.ApplicationContextAware接口，该bean私有的声明过程
调用【ApplicationContextAware】--setApplicationContext
//11.后置处理器-前
 ======================postProcessBeforeInitialization==============================
BeanPostProcessor调用postProcessBeforeInitialization方法：beanName为myDefineBeanName
//12. @PostConstruct注解初始化回调
注解@PostConstruct自定义初始化方法[myInit]
//13. InitializingBean接口初始化回调
调用【InitializingBean】--afterPropertiesSet
//13. @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")注解初始化initMethod回调
注解@Bean自定义初始化方法[initMethod]
//14. 后置处理器-后
======================postProcessAfterInitialization==============================
BeanPostProcessor调用postProcessAfterInitialization方法：beanName为myDefineBeanName

//15. ioc环境已准备好，打印了项目启动完成日志
Started SpringbootBeanLifecycleApplication in 0.579 seconds (JVM running for 1.194)

//程序开始退出，销毁bean
//16.@PreDestroy注解销毁bean回调
注解@PreDestroy自定义销毁方法[myDestroy]
//16.DisposableBean接口销毁bean回调
调用【DisposableBean】--destroy
// 17.@Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")注解销毁destroyMethod回调
注解@Bean自定义销毁方法[destroyMethod]
```
# bean生命过程图
![bean生命周期.png](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/6ee20540c47f46cead019932c134ce90~tplv-k3u1fbpfcp-watermark.image?)

- 👍🏻：有收获的，点赞鼓励！
- ❤️：收藏文章，方便回看！
- 💬：评论交流，互相进步！
