package com.xiaoboer.ssm.controller;

import com.xiaoboer.ssm.pojo.Admin;
import com.xiaoboer.ssm.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminAction {

    @Autowired
    AdminService adminService;

    @RequestMapping("/login")
    public String login(String name, String pwd, Model model){
        Admin admin = adminService.login(name, pwd);

        if (admin != null){
            model.addAttribute("admin", admin);
            return "main";
        }else {
            model.addAttribute("errmsg", "用户名或密码不正确！");
            return "login";
        }
    }
}
