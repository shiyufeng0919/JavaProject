package com.syf.springbootdubbo.service.impl;

import com.syf.springbootdubbo.domain.UserEntity;
import com.syf.springbootdubbo.service.UserService;
import com.alibaba.dubbo.config.annotation.Service;

/*
* 服务实现
* */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        userEntity.setId(Long.parseLong("1001"));
        System.out.println(userEntity.toString());
        return userEntity;
    }
}
