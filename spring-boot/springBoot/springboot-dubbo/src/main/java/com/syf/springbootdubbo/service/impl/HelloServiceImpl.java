package com.syf.springbootdubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.syf.springbootdubbo.service.HelloService;

@Service(version = "1.0.0")
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello,"+name;
    }
}
