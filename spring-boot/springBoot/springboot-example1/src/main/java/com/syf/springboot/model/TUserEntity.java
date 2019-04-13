package com.syf.springboot.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_user")
public class TUserEntity implements Serializable {

    /*
    * 注：@GeneratedValue(strategy = GenerationType.IDENTITY)注解不能写成@GeneratedValue不则会报
    * springBoot+jpa 测试自增时数据库报错Springboot-jpa Table 'bootdb.hibernate_sequence' doesn't exist
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="t_id")
    private Long id;

    @Column(name = "t_name")
    private String name;

    @Column(name = "t_age")
    private int age;

    @Column(name="t_address")
    private String address;

    //第6章，测试拦截器修改表t_user新添加字段-密码字段
    @Column(name="t_pwd")
    private String pwd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
