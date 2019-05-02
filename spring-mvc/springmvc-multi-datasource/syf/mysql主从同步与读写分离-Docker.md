# 利用docker实现mysql主从同步/读写分离

参考[利用docker实现mysql主从同步/读写分离，附赠docker搭建mycat读写分离](http://www.sunhao.win/articles/mysql-replication.html)

## 一。利用docker实现mysql主从同步/读写分离

### 1.[安装Docker](resources/deploy-docker.sh),下载mysql镜像

    $ docker pull mysql:5.7
    
### 2.利用mysql镜像，创建用于主从同步的两个新镜像

    我们当前所在的服务器叫宿主服务器，
    
    我们要利用docker 虚拟两个docker容器服务器，一个主服务器，一个从服务器
    
#### 2-1 .创建master(主)和slave(从)两个文件夹

    $ mkdir -p /usr/mysql/master   #主
    
    $ mkdir -p /usr/mysql/slave    #从
    
#### 2-2 .在master和slave文件夹下 创建 Dockerfile和my.cnf,并构建镜像

    **master主**

    $ cd /usr/mysql/master

    $ vim Dockerfile
    
    # add content
    FROM mysql:5.7
    COPY my.cnf /etc/mysql/
    EXPOSE 3306
    CMD ["mysqld"]
    
    $ vim my.cnf
    
    # add content
    [mysqld]
    log-bin=mysql-bin   #[必须]启用二进制日志
    server-id=1   #[必须]服务器唯一ID，默认是1，一般取IP最后一段，这里看情况分配
    
    #构建镜像
    $ docker build -t master/mysql .  #注意.当前目录
    
    **slave从**
    
    $ cd /usr/mysql/slave
    
    $ vim Dockerfile
    
    # add content
    FROM mysql:5.7
    COPY my.cnf /etc/mysql/  
    EXPOSE 3306
    CMD ["mysqld"]
    
    $ vim my.cnf
    [mysqld]
    log-bin=mysql-bin   #[必须]启用二进制日志
    server-id=2         #[必须]服务器唯一ID，默认是1，一般取IP最后一段，这里看情况分配
    
    #构建镜像
    $docker build -t slave/mysql . #注意点.当前目录
    
    
    #查看镜像
    $ docker images 或 docker image ls | grep mysql
    REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
    slave/mysql         latest              9f49a5963025        44 minutes ago      372 MB
    master/mysql        latest              ee724f107a4e        51 minutes ago      372 MB
    mysql               5.7                 98455b9624a9        2 weeks ago         372 MB

#### 2-3 用镜像创建容器

    #主
    $ docker run -p 3306 --name mysql-master -e MYSQL_ROOT_PASSWORD=syf -d master/mysql
    #从
    $ docker run -p 3306 --name mysql-slave -e MYSQL_ROOT_PASSWORD=syf -d slave/mysql
    
    #查看运行的容器,记住master/mysql对外端口号32771
    $ docker ps
    CONTAINER ID   IMAGE            COMMAND                  CREATED             STATUS              PORTS                                      NAMES
    41bacde7277d   slave/mysql      "docker-entrypoint..."   46 minutes ago      Up 46 minutes       33060/tcp, 0.0.0.0:32772->3306/tcp         mysql-slave
    6fa50b7ea201   master/mysql     "docker-entrypoint..."   50 minutes ago      Up 50 minutes       33060/tcp, 0.0.0.0:32771->3306/tcp         mysql-master

#### 2-4 mysql主从配置

##### (1)。进入master容器 -主库配置

    $ docker exec -it mysql-master bash
    root@6fa50b7ea201:/# mysql -uroot -psyf  #进入mysql
    
    #输入命令
    
    mysql>GRANT REPLICATION SLAVE ON *.* TO 'root'@'192.168.1.106' IDENTIFIED BY 'syf';  #指定ip(本机ip为192.168.1.106)
    
    或
    
    mysql>GRANT REPLICATION SLAVE ON *.* to 'root'@'%' identified by 'syf';  #（所有ip）(密码为主库密码)
    
    #查看主容器数据库状态
    mysql> show master status;
    +------------------+----------+--------------+------------------+-------------------+
    | File             | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
    +------------------+----------+--------------+------------------+-------------------+
    | mysql-bin.000003 |     2632 |              |                  |                   |
    +------------------+----------+--------------+------------------+-------------------+
    
**注：主库不要做任何操作，以免状态改变。记录File和Position**
    
##### (2)。进入slave容器 -从库配置

    $ docker exec -it mysql-slave bash
    root@41bacde7277d:/# mysql -uroot -psyf
    
    mysql>change master to
    master_host='192.168.1.106',         #master主机ip
    master_user='root',                  #master主库GRANT REPLICATION配置的用户名
    master_log_file='mysql-bin.000003',  #master主容器数据库状态中的File
    master_log_pos=2632,                 #master主容器数据库状态中的Position
    master_port=32771,                   #mysql-master容器对外暴露的端口($ docker ps)
    master_password='syf';               #master主库GRANT REPLICATION配置的密码
    
    mysql>start slave # 启动从服务器复制功能
    
    mysql> show slave status\G 检查主从连接状态
    *************************** 1. row ***************************
                   Slave_IO_State: Waiting for master to send event
                      Master_Host: 192.168.1.106
                      Master_User: root
                      Master_Port: 32771
                    Connect_Retry: 60
                  Master_Log_File: mysql-bin.000003
              Read_Master_Log_Pos: 449
                   Relay_Log_File: 41bacde7277d-relay-bin.000002
                    Relay_Log_Pos: 320
            Relay_Master_Log_File: mysql-bin.000003
                 Slave_IO_Running: Yes     #必须为yes
                Slave_SQL_Running: Yes     #必须为yes
                ......
    
##### (3)。测试主从连接

**注意设置主从后，操作只能在master终端上，slave上的操作不会同步到master上**

    #登录主master数据库
    
    mysql> create database syf;
    Query OK, 1 row affected (0.00 sec)
    
    mysql> use syf;
    Database changed
    mysql> create table t_user(id int(2),name varchar(10));
    Query OK, 0 rows affected (0.01 sec)
    
    mysql> insert into t_user(id,name)values(1,'syf');
    Query OK, 1 row affected (0.00 sec)
    
    #登录从slave数据库,验证
    mysql> use syf
    mysql> select * from t_user;
    +------+------+
    | id   | name |
    +------+------+
    |    1 | syf  |
    +------+------+
    
**如果主服务器已经存在应用数据，则在进行主从复制时，需要单独复制处理（注意此操作，如果对从服务器单独录入的数据，会被覆盖掉）**

##### (4)。小测试

##### master主库容器

    (1)在主服务器数据库插入新的数据，并进行锁表操作，不让数据再进行写入动作
    mysql> insert into t_user(id,name)values(2,'kaixin');
    Query OK, 1 row affected (0.00 sec)
    
    mysql> FLUSH TABLES WITH READ LOCK;
    Query OK, 0 rows affected (0.00 sec)
    
    mysql> show full processlist;
    +----+------+------------------+------+-------------+------+---------------------------------------------------------------+-----------------------+
    | Id | User | Host             | db   | Command     | Time | State                                                         | Info                  |
    +----+------+------------------+------+-------------+------+---------------------------------------------------------------+-----------------------+
    |  5 | root | 172.17.0.1:47610 | NULL | Binlog Dump | 3718 | Master has sent all binlog to slave; waiting for more updates | NULL                  |
    |  6 | root | localhost        | syf  | Query       |    0 | starting                                                      | show full processlist |
    +----+------+------------------+------+-------------+------+---------------------------------------------------------------+-----------------------+
    2 rows in set (0.00 sec)
    
    (2)退出mysql，用mysqldump备份数据文件到/var/lib,然后顺便多余的用tar打包一下玩
    mysql> exit
    Bye
    root@6fa50b7ea201:/# mysqldump -uroot -p syf > /var/lib/syf.dump  #注syf是数据库名
    Enter password: 
    root@6fa50b7ea201:/# cd /var/lib
    root@6fa50b7ea201:/var/lib# ls
    apt  dpkg  misc  mysql	mysql-files  mysql-keyring  pam  syf.dump  systemd
    root@6fa50b7ea201:/var/lib# tar -zcvf ./syf.dump.tar ./syf.dump 
    ./syf.dump
    root@6fa50b7ea201:/var/lib# ls
    apt  dpkg  misc  mysql	mysql-files  mysql-keyring  pam  syf.dump  syf.dump.tar  systemd
    root@6fa50b7ea201:/var/lib# 

##### 退出容器，进入宿主机
  
    (3)打开宿主服务器，复制mysql主服务器文件syf.dump.tar。到宿主服务器
    [root@node6 ~]# mkdir -p /var/mydata
    [root@node6 ~]# docker cp 6fa50:/var/lib/syf.dump.tar /var/mydata/
    [root@node6 ~]# ls /var/mydata/
    syf.dump.tar
    
    (4)复制syf.dump.tar到从服务器
    docker cp /var/mydata/syf.dump.tar 41bac:/var/lib   #41bac为从容器id

##### slave从库容器
    
    (5)从容器执行
    root@41bacde7277d:/# cd /var/lib/
    root@41bacde7277d:/var/lib# ls
    apt  dpkg  misc  mysql	mysql-files  mysql-keyring  pam  syf.dump.tar  systemd
    root@41bacde7277d:/var/lib# tar -zxvpf syf.dump.tar 
    ./syf.dump
    root@41bacde7277d:/var/lib# ls
    apt  dpkg  misc  mysql	mysql-files  mysql-keyring  pam  syf.dump  syf.dump.tar  systemd
    root@41bacde7277d:/var/lib# mysql -uroot -psyf syf < /var/lib/syf.dump;  # 第一个syf是密码，第二个syf是数据库名
    mysql: [Warning] Using a password on the command line interface can be insecure.   #写入成功
    
    #进入mysql查询数据即可
    
##### master主数据库

    #取消主服务器数据库锁定
    mysql> UNLOCK TABLES;
    
---------------------
