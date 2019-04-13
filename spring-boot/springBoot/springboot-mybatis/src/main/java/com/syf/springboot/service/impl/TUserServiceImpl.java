package com.syf.springboot.service.impl;

import com.syf.springboot.dao.TUserMapper;
import com.syf.springboot.model.TUserModel;
import com.syf.springboot.service.TUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TUserServiceImpl implements TUserService {

    @Resource
    private TUserMapper tUserMapper;

    @Override
    public TUserModel selectById(Integer id) {
        return tUserMapper.selectById(id);
    }
}
