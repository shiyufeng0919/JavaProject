package com.syf.springboot.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
  第6章 springboot添加拦截器

* SessionInterceptor拦截器  add by shiyufeng 2019.4.6
*
* HandlerInterceptor:springmvc内部接口
* */
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object obj) throws Exception{
        System.out.println("###拦截器开始");
        //登录不做拦截
        if(request.getRequestURI().equals("/user/login") || request.getRequestURI().equals("/user/login_view")){
            return true;
        }
        //验证session是否存在
        Object object=request.getSession().getAttribute(("session_user"));
        if (object == null){
            System.out.println("###获取session_user为空");
            response.sendRedirect("/user/login_view");//跳转到login页面
            return false;
        }
        System.out.println("###获取session_user不为空");
        return true;
    }
}
