package com.ljw.springbootaop.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.FixedValue;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/7 15:31
 */
public class FixedValueTest {
    public static void main(String[] args) throws Exception {
        HelloWorld hello = new HelloWorld();
        Enhancer enhancer = new Enhancer();
        //设置代理目标
        enhancer.setSuperclass(hello.getClass());
        //设置单一回调对象，在调用中拦截对目标方法的调用,返回固定的值
        enhancer.setCallback(new FixedValue() {

            @Override
            public Object loadObject() throws Exception {
                // TODO Auto-generated method stub
                return "FixedValue";
            }
        });
        //设置类加载器
        enhancer.setClassLoader(hello.getClass().getClassLoader());
        HelloWorld o =(HelloWorld) enhancer.create();
        //Object obj = enhancer.create();
        System.out.println(o.say(false));
    }
}
