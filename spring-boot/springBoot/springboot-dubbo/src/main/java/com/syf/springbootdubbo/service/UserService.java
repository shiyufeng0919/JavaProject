package com.syf.springbootdubbo.service;

import com.syf.springbootdubbo.domain.UserEntity;

/*
* 服务提供主要包括两个部分:
* 1。暴露服务 -interface
* 2。服务实现 -interface实现类
* */
/*
* 暴露服务
* */
public interface UserService {
    UserEntity saveUser(UserEntity userEntity);
}
