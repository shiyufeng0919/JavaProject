package com.syf.service.impl;

import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.StringUtils;
import com.syf.model.TUser;
import com.syf.redis.RedisUtils;
import com.syf.service.ITUserService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class RedisCacheTest {

    private static Logger logger = Logger.getLogger(ITUserServiceImplTest.class);
    //注入Redis工具类
    @Autowired
    private RedisUtils redisUtils;

    @Resource
    private ITUserService itUserService;

    /*
    * 测试redis单机版
    * */
    @Test
    public void queryByRedis() {
        String key = "redis-key";
        // 1.从缓存中命中
        try {
            String redisJson = redisUtils.get(key);
            logger.info("redisJson:"+redisJson);
            /*
            * StringUtils.isNotBlank与StringUtils.isNullOrEmpty()区别
            * */
            if(!StringUtils.isNullOrEmpty(redisJson)){
                logger.info("redisJson is not null");
                logger.info("redisJson:"+redisJson);
            }else{
                // 2.如果没用命中，执行原有逻辑，从数据库中获取数据
                // 未实现，从数据库中获取数据
                String json = getFromDB();

                logger.info("getFromDB json:"+json);

                // 3.将查询出来的结果加入缓存
                try {
                    //向redis写入数据，则配置文件中需要redis配置成主redis，因为从redis写入会不成功
                    this.redisUtils.set(key, json);
                    this.redisUtils.expire(key, 60 * 60 * 24);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //查询数据库
    public String getFromDB() {
        //走从库
        TUser tUser=itUserService.getTUserById(1);
        logger.info(JSON.toJSONString(tUser)); //{"age":30,"id":1,"password":"123","userName":"syf"}
        return JSON.toJSONString(tUser);
    }

}
