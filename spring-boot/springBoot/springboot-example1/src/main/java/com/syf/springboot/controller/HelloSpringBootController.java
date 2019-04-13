package com.syf.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
* @RestController声明HelloSpringBootController是一个访问控制器
*
* @RequestMapping注解声明index1()方法可以通过http://127.0.0.1:8080/index1访问到
* */
@RestController
@RequestMapping
public class HelloSpringBootController {
    /*
    * 测试输出Hello-SpringBoot
    *
    * 请求url:http://127.0.0.1:8080/index1 浏览器输出:Hello-SpringBoot
    * */
    @RequestMapping(value ="/index1",method = RequestMethod.GET)
    public String index1(){
        return "Hello-SpringBoot";
    }
}
