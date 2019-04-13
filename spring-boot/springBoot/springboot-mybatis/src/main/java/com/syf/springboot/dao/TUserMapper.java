package com.syf.springboot.dao;

import com.syf.springboot.model.TUserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/*
* 注解@Mapper必须，否则启动springboot报错 A component required a bean of type
* */
@Mapper
public interface TUserMapper{
    TUserModel selectById(@Param("id") Integer id);
}
