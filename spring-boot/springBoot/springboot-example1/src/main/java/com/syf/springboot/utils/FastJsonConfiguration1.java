package com.syf.springboot.utils;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/*
* SpringBoot整合FastJson内容  add by shiyufeng 2019.4.6
* @Configuration注解让SpringBoot自动加载类内的配置
*
* 注：WebMvcConfigurerAdapter这个类是SpringBoot内部提供专门处理用户自行添加的配置，
*    里面不仅仅包含了修改视图的过滤还有拦截器，过滤器，Cors配置
* */
@Configuration
public class FastJsonConfiguration1 extends WebMvcConfigurerAdapter { //WebMvcConfigurerAdapter过时
    /**
     * 修改自定义消息转换器
     * 消息转换器列表
    * */
    public void configureMessgeConverters(List<HttpMessageConverter<?>> converterList){
        //调用父类的配置
        super.configureMessageConverters(converterList);
        //创建fastJson消息转换器
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter=new FastJsonHttpMessageConverter();
        //创建配置类
        FastJsonConfig fastJsonConfig=new FastJsonConfig();
        //修改配置返回内容的过滤
        /*
        * fastJson配置实体调用setSerializerFeatures方法可配置多个过滤方式
        * 常用setSerializerFeatures配置
        * 1.WriteNullListAsEmpty  ：List字段如果为null,输出为[],而非null
        * 2.WriteNullStringAsEmpty ： 字符类型字段如果为null,输出为"",而非null
        * 3.DisableCircularReferenceDetect ：消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
        * 4.WriteNullBooleanAsFalse：Boolean字段如果为null,输出为false,而非null
        * 5.WriteMapNullValue：是否输出值为null的字段,默认为false
        * */
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,//消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
                SerializerFeature.WriteMapNullValue,  //是否输出值为null的字段,默认为false
                SerializerFeature.WriteNullListAsEmpty //字符类型字段如果为null,输出为"",而非null
        );
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        //将fastjson添加到视图消息转换器列表内
        converterList.add(fastJsonHttpMessageConverter);
    }

}
