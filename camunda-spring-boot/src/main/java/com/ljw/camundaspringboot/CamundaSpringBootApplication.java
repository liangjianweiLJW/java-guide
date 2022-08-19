package com.ljw.camundaspringboot;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

import javax.annotation.Resource;

@SpringBootApplication
@EnableProcessApplication
public class CamundaSpringBootApplication {

    @Resource
    private RuntimeService runtimeService;

    public static void main(String[] args) {
        SpringApplication.run(CamundaSpringBootApplication.class, args);
    }


    @EventListener
    public void processPostDeploy(PostDeployEvent event) {
        runtimeService.startProcessInstanceByKey("audit");
    }

}
