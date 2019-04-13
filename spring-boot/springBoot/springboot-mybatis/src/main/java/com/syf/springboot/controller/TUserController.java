package com.syf.springboot.controller;

import com.syf.springboot.model.TUserModel;
import com.syf.springboot.service.TUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/*
* 用户控制器 add by shiyufeng 2019.4.6
*
* @RestController作用：是由@Controller和@ResponseBody组成，表示该类是controller和返回的结果为JSON数据，不是页面路径。
* */
@RestController
@RequestMapping("tuser")
public class TUserController {

    @Resource
    private TUserService tUserService;

    @GetMapping("/selectById")
    public TUserModel selectById(Integer id){
        return tUserService.selectById(id);
    }
}
