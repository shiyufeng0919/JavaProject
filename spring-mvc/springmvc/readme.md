## spring + springmvc + mybatis + maven框架搭建  ---开心玉凤 2019.4.13

--------------------------------------------

### 一。概念

#### 1.Spring

    Spring 是一个开源框架，它是为了解决企业应用开发的复杂性而创建的。
     
    Spring 使用基本的 JavaBean 来完成以前只可能由 EJB 完成的事情。
    
    然而， Spring 的用途不仅限于服务器端的开发。从简单性、可测试性和松耦合的角度而言，任何 Java 应用都可以从 Spring 中受益。 
    
    简单来说， Spring 是一个轻量级的控制反转（ IoC ）和面向切面（ AOP ）的容器框架。

#### 2.SpringMVC

    Spring MVC属于 SpringFrameWork 的后续产品，已经融合在 Spring Web Flow 里面。 
    
    Spring MVC分离了控制器、模型对象、分派器以及处理程序对象的角色，这种分离让它们更容易进行定制。
      
#### 3.MyBatis

    MyBatis本是apache的一个开源项目iBatis, 2010 年这个项目由 apache software foundation 迁移到了 google code ，并且改名为 MyBatis
    
    MyBatis是一个基于Java的持久层框架。iBATIS提供的持久层框架包括 SQL Maps 和 Data Access Objects （ DAO ） 
    
    MyBatis消除了几乎所有的JDBC代码和参数的手工设置以及结果集的检索。 
    
    MyBatis使用简单的XM或注解用于配置和原始映射，将接口和Java的POJOs （ Plain Old Java Objects ，普通的Java 对象）映射成数据库中的记录。

--------------------------------------------

### 二。构建maven项目-SSM(Spring+SpringMVC+Mybatis)

参考:[SSM三大框架整合详细总结（Spring+SpringMVC+MyBatis）](https://cloud.tencent.com/developer/article/1041053?fromSource=waitui)

#### IDEA创建Maven项目

    框架版本：

    Spring 4.0.2 RELEASE
    
    Spring MVC 4.0.2 RELEASE
    
    MyBatis 3.2.6

##### 1.添加依赖pom.xml

##### 2.整合spring 与 mybatis

###### 2-1 建立jdbc.propreties,配置数据源

###### 2-2 创建测试用的数据库和表

    create database springmvcdb
    
    CREATE TABLE `t_user` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `user_name` varchar(40) NOT NULL,
      `password` varchar(255) NOT NULL,
      `age` int(4) NOT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
    
    insert into `t_user`(`id`,`user_name`,`password`,`age`) values (1,'syf','123',30);

###### 2-3 利用MyBatis Generator自动创建代码

参考：[IDEA 2018集成MyBatis Generator 插件 详解](https://blog.csdn.net/yangqinfeng1121/article/details/80183516)

    (1).pom.xml添加"集成MyBatis Generator 插件"
    (2).编写generatorConfig.xml
    (3).右上角->Edit Configurations..->+号->maven->
        命令：mybatis-generator:generate -e
    (4)执行即可生成Tuser.java / TUserMapper.java / TUserMapper.xml
        
![](syf/images/1.jpg)

###### 2-4 建立service接口(ITUserService)和实现类(ITUserServiceImpl)

###### 2-5 建立测试类，测试spring+mybatis整合是否通过

    (1).src/main目录下创建test测试目录
    (2).在service实现类文件ITUserServiceImpl.java中，选中ITUserServiceImpl
    (3).command+shift+T 自动创建测试类，选择junit4
    (4).测试文件会创建在test目录下面:ITUserServiceImplTest.java
    (5).编写测试内容，执行，成功
    
##### 3.整合springmvc --在web.xml中配置整合

###### 3-1 创建spring-mvc.xml配置文件

###### 3-2 重构web.xml配置文件，配置整合

###### 3-3 创建Controller类(TUserController)

###### 3-4 部署tomcat项目，项目名称/

###### 3-5 浏览器访问http://localhost:8080/user/toTuser?id=1,浏览器打印显示结果syf

--------------------------------------------


