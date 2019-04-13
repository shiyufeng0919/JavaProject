package com.syf.controller;

import com.alibaba.fastjson.JSON;
import com.syf.model.TUser;
import com.syf.service.ITUserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class TUserController {
    private static Logger logger = Logger.getLogger(TUserController.class);
    @Resource
    private ITUserService itUserService;

    @RequestMapping("/toTuser")
    public String toTuser(HttpServletRequest request, Model model){
        int userId = Integer.parseInt(request.getParameter("id"));
        TUser tUser=itUserService.getTUserById((userId));
        model.addAttribute("tUser", tUser);
        logger.info(JSON.toJSONString(tUser));
        return "tuser";
    }
}
