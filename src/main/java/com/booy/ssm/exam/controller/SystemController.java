package com.booy.ssm.exam.controller;

import com.booy.ssm.exam.pojo.Menu;
import com.booy.ssm.exam.pojo.User;
import com.booy.ssm.exam.service.MenuService;
import com.booy.ssm.exam.service.UserService;
import com.booy.ssm.exam.utils.ExamConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SystemController {
    @Autowired
    UserService userService;

    @Autowired
    MenuService menuService;

    //默认登录页
    @RequestMapping("login.html")
    public String login(){
        return "login";
    }

    @RequestMapping("error.html")
    public String error(){
        return "error";
    }

    //主页菜单树
    @RequestMapping("index.html")
    public String index(Model model,HttpSession session){
        User user =(User) session.getAttribute(ExamConstants.SESSION_USER);
        List<Menu> menuList = menuService.getUserMenuList(user.getId());
        model.addAttribute("menuList",menuList);
        session.setAttribute(ExamConstants.USER_MENU,menuList);//把菜单树存放在session里
        return "index";
    }

    //用户登录
    @RequestMapping("dologin.html")
    public String dologin(String account, String password, Model model, HttpSession session){
        User user = userService.dologin(account, password);
        if(user==null){
            model.addAttribute("message","用户名或密码错误！");
            return "login";
        }
        session.setAttribute(ExamConstants.SESSION_USER,user);
        return "redirect:index.html";
    }
    //用户注销
    @RequestMapping("logout.html")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }
}
