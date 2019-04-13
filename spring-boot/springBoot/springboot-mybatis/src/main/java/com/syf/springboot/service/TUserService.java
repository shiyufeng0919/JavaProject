package com.syf.springboot.service;

import com.syf.springboot.model.TUserModel;

/*
* Service接口
* */
public interface TUserService {
    TUserModel selectById(Integer id);
}
