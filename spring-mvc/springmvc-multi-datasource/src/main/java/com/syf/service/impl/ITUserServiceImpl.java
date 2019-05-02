package com.syf.service.impl;

import com.syf.dao.TUserMapper;
import com.syf.datasource.DataSourceChange;
import com.syf.model.TUser;
import com.syf.service.ITUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/*
* @service 服务:注入dao
* */
@Service
public class ITUserServiceImpl implements ITUserService {

    @Resource
    private TUserMapper tUserMapper;

    /*
    * 从库
    * */
    @Override
    @DataSourceChange(slave = true)
    public TUser getTUserById(int userId) {
        System.out.println("进入getTUserById()##");
        return tUserMapper.selectByPrimaryKey(userId);
    }

    /*
    * 主库
    * */
    @Override
    @DataSourceChange(slave = false)
    public TUser getUserById(int userId) {
        System.out.println("进入getUserById()##");
        return tUserMapper.selectByPrimaryKey(userId);
    }


}
