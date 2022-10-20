package com.ljw.camundaspringboot;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableProcessApplication
public class CamundaSpringBootApplication {

    @Resource
    private RuntimeService runtimeService;

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(CamundaSpringBootApplication.class, args);

        Environment env = application.getEnvironment();

        String ip = InetAddress.getLocalHost().getHostAddress();
        //https://blog.csdn.net/wmsuccess/article/details/101781967
        //String port2 = env.getProperty("server.port");
        String port = env.getProperty("local.server.port");
        String path = env.getProperty("server.servlet.context-path", "").trim();
        System.out.println("\n----------------------------------------------------------\n\t" +
                "服务入库: \thttp://" + ip + ":" + port + path + "\n" +
                "----------------------------------------------------------");
        System.out.println("\n----------------------------------------------------------\n\t" +
                "Swagger文档: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------");
    }


    @EventListener
    public void processPostDeploy(PostDeployEvent event) {
        //runtimeService.startProcessInstanceByKey("audit");
        //runtimeService.startProcessInstanceByKey("Process_myljw");
        //runtimeService.startProcessInstanceByKey("exGateway2");
    }

}
