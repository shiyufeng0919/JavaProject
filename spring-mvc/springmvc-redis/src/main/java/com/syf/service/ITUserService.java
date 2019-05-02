package com.syf.service;

import com.syf.model.TUser;

public interface ITUserService {
    //测试从库
    public TUser getTUserById(int userId);
    //测试主库
    public TUser getUserById(int userId);
}
