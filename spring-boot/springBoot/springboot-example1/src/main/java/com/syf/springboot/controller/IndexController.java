package com.syf.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
* SpringBoot与jsp整合 2019.4.5
* */
@Controller
@RequestMapping("/user")
public class IndexController {
    /*
    * 访问:webapp/jsp/index.jsp文件
    *
    * 浏览器请求:http://127.0.0.1:8080/user/index2 ，响应结果:welcome come in index.jsp | learn springBoot ing...
    * */
    @RequestMapping(value = "/index2",method = RequestMethod.GET)
    public String index2(){
        return "index";//springmvc会找/WEB-INF/jsp/前缀，index.jsp后缀为jsp的文件
    }

    /*
    * 第6章 springBoot添加拦截器
    * */
    /*
    * 初始化登录页面
    * */
    @RequestMapping(value = "/login_view",method = RequestMethod.GET)
    public String login_view(){
        return "login";
    }

    @RequestMapping(value = "/login_index",method = RequestMethod.GET)
    public String login_index(){
        return "index";
    }
}
