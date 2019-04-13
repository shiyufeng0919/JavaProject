package com.syf.springboot.utils;

import com.syf.springboot.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
* 将SessionInterceptor拦截器添加到springboot的配置中
* */
@Configuration
public class SessionConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //添加拦截路径
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**");
    }
}
