package com.syf.service.impl;

import com.alibaba.fastjson.JSON;
import com.syf.model.TUser;
import com.syf.service.ITUserService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class ITUserServiceImplTest {

    private static Logger logger = Logger.getLogger(ITUserServiceImplTest.class);

    @Resource
    private ITUserService itUserService;

    /*
    * 测试数据走从库
    * */
    @Test
    public void getTUserById() {
        TUser tUser=itUserService.getTUserById(1);
        logger.info(JSON.toJSONString(tUser)); //{"age":30,"id":1,"password":"666","userName":"syf"}
    }

    /*
    * 测试数据走主库
    * */
    @Test
    public void getUserById() {
        TUser tUser=itUserService.getUserById(1);
        logger.info(JSON.toJSONString(tUser)); //{"age":30,"id":1,"password":"123","userName":"syf"}
    }
}