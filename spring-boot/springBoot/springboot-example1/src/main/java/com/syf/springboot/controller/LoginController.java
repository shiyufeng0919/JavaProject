package com.syf.springboot.controller;

import com.syf.springboot.jpa.TUserJPA;
import com.syf.springboot.model.TUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class LoginController {

    /*
    * TUserJPA使用SpringBean的形式注入
    * */
    @Autowired
    private TUserJPA tUserJPA;

    @RequestMapping(value = "/login")
    public String login(TUserEntity tuser, HttpServletRequest request){
        //登录成功
        boolean flag=true;
        String result="login success";
        //根据用户名查询用户是否存在
        TUserEntity tUserEntity=tUserJPA.getOne(Long.parseLong("1"));//此处先固定给个值
//        Optional<TUserEntity> tUserEntity=tUserJPA.findOne(new Specification<TUserEntity>() {
//            @Override
//            public Predicate toPredicate(Root<TUserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                criteriaQuery.where(criteriaBuilder.equal(root.get("name"),tuser.getName()));
//                return null;
//            }
//        });
        if(tUserEntity==null){
            flag=false;
            result="用户不存在，登录失败";
        }else if(!tUserEntity.getPwd().equals(tuser.getPwd())){
            flag=false;
            result="密码不相符，登录失败";
        }
        if (flag){
            //设置session
            request.getSession().setAttribute("session_user",tUserEntity);
        }
        return result;
    }
}
