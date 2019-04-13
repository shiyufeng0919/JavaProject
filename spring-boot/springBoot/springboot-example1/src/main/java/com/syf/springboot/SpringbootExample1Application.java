package com.syf.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
* 注解@SpringBootApplication声明当前类为springboot的入口类
*
* 一个springboot项目内有且只能有一个@SpringBootApplication注解存在
* */
@SpringBootApplication
public class SpringbootExample1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootExample1Application.class, args);
    }

}
