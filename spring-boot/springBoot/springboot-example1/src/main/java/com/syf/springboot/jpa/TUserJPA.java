package com.syf.springboot.jpa;

import com.syf.springboot.model.TUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/*
* SpringDataJPA内部使用了类代理的方式让继承了它接口的子接口都以spring管理的Bean的形式存在，
* 也就是说可直接使用@Autowired注解在spring管理bean使用。
* */
public interface TUserJPA extends
        JpaRepository<TUserEntity,Long>,  //JpaRepository是SpringDataJPA提供的简单数据操作接口
        JpaSpecificationExecutor<TUserEntity>, //JpaSpecificationExecutor是SpringDataJPA提供的复杂查询接口
        Serializable { //Serializable序列化接口
}
