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
    @Test
    public void getTUserById() {
        TUser tUser=itUserService.getTUserById(1);
        logger.info(JSON.toJSONString(tUser));
    }
}