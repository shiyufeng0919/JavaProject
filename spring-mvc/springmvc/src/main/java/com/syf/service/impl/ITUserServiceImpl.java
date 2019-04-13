package com.syf.service.impl;

import com.syf.dao.TUserMapper;
import com.syf.model.TUser;
import com.syf.service.ITUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ITUserServiceImpl implements ITUserService {

    @Resource
    private TUserMapper tUserMapper;
    @Override
    public TUser getTUserById(int userId) {
        return tUserMapper.selectByPrimaryKey(userId);
    }
}
