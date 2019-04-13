package com.syf.springboot.controller;

import com.syf.springboot.jpa.TUserJPA;
import com.syf.springboot.model.TUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/tuser")
public class TUserController {
    @Autowired
    private TUserJPA tUserJPA;

    /*
    * 查询用户列表方法
    * */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<TUserEntity> list(){
        return tUserJPA.findAll();
    }

    /*
    * 添加，更新用户方法
    * */
    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public TUserEntity save(TUserEntity tUserEntity){
        return tUserJPA.save(tUserEntity); //save方法可执行添加也可执行更新
    }

    /*
    * 删除用户方法
    * */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public List<TUserEntity> delete(Long id){
        tUserJPA.deleteById(id);
        return tUserJPA.findAll();
    }


}
