package com.syf.springboot.core.configurer;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/*
* @Configuration表示该文件是一个配置文件
* */
@Configuration
public class MybatisConfigurer {

    /*
    * @Bean表示该方法是一个传统xml配置文件中的<Bean id=""></Bean>
    * */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception{
        SqlSessionFactoryBean factoryBean=new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        //设置项目中Model的存储路径
        factoryBean.setTypeAliasesPackage("com.syf.springboot.model");
        //添加xml目录
        ResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
        //设置项目中mapper.xml存储路径
        factoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return factoryBean.getObject();
    }

    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        //设置dao层的存储路径
        mapperScannerConfigurer.setBasePackage("com.syf.springboot.dao");
        return mapperScannerConfigurer;
    }
}
