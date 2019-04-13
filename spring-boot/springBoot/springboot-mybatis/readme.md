### [构建springboot项目-mybatis版本](https://juejin.im/post/5ad6b3c3f265da237c696ba0)

    表结构：由项目springboot创建t_user
    
    create table t_user(t_id int(11)  NOT NULL auto_increment,t_name varchar(30),t_age int(10),t_address varchar(100),t_pwd varchar(8),PRIMARY KEY(t_id))ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
    地址栏请求:http://localhost:8888/tuser/selectById?id=1 返回结果:{"id":1,"name":"syf","age":18,"address":"haerbin","pwd":"123"}

### [springboot整合mybatis-plus]

    mybatis增强工具: ourbatis,mybatis-generator,mybatis-plus

    mybatis-plus是为了提高效率和简化配置
    
#### mybatis-plus提供了BaseMapper、BaseService这些基类来提供一些操作的支持，比如

    save(T t)
    saveOrUpdate(T t)
    update(T t, Wrapper<T> wrapper)
    page(IPage<T> page, Wrapper<T> queryWrapper)
