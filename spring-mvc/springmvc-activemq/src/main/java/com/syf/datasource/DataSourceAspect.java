package com.syf.datasource;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.aspectj.lang.ProceedingJoinPoint;

/*
* add by shiyufeng 2019.4.30 定义切面。用于实现分库
*
* AOP切面
* 注解：
* @Aspect:作用是把当前类标识为一个切面供容器读取
* @Component:把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>
* @EnableAspectJAutoProxy(proxyTargetClass = true) //使AspectJ注解起作用：自动为匹配的类生成代理对象，加入 aop 自动扫描 DataSourceAspect 配置数据库注解aop
* @Order:标记定义了组件的加载顺序.值越小拥有越高的优先级
* */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Order(1)
public class DataSourceAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceAspect.class);

    /*
    * @Around:环绕增强，相当于MethodInterceptor
    *
    * 根据指定的注解选择主库还是从库
    * */
    @Around("@annotation(dataSourceChange)")
    public Object doAround(ProceedingJoinPoint pjp, DataSourceChange dataSourceChange) throws Exception {
        System.out.println("doAround###");
        Object retVal = null;
        boolean selectedDataSource = false;
        try {
            if (null != dataSourceChange) {
                System.out.println("dataSourceChange="+dataSourceChange);
                selectedDataSource = true;
                if (dataSourceChange.slave()) {
                    LOGGER.info("选择从库");
                    DynamicDataSource.useSlave();
                } else {
                    LOGGER.info("选择主库");
                    DynamicDataSource.useMaster();
                }
            }
            retVal = pjp.proceed();
        } catch (Throwable e) {
            LOGGER.warn("数据源切换错误", e);
            throw new DataSourceAspectException("数据源切换错误", e);
        } finally {
            if (selectedDataSource) {
                DynamicDataSource.reset();
            }
        }
        return retVal;
    }
}

/*
* 上述相当于在spring-mybatis.xml中定义：
*
*   <aop:aspectj-autoproxy proxy-target-class="true"/>
	<!--切面-->
    <bean id="DataSourceAspect" class="com.syf.datasource.DataSourceAspect"></bean>
* */
