package com.example.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class ConsumerApp {

    public static void main(String[] args) throws Exception {
		
		ApplicationContext applicationContext = SpringApplication.run(ConsumerApp.class, args);
		
		//初始化
		applicationContext.getBean(AmqpConfig.class).init("tmm-test");
		

    }
}
