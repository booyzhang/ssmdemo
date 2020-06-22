package com.booy.ssm.exam.interceptor;

import com.booy.ssm.exam.pojo.Menu;
import com.booy.ssm.exam.utils.ExamConstants;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MenuInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        List<Menu> menus = (List<Menu>)request.getSession().getAttribute(ExamConstants.USER_MENU);
        if(check(menus,request.getServletPath())){
            return true;
        }
        response.sendRedirect("/error.html");
        return false;
    }

    public boolean check(List<Menu> menus,String path){
        for(Menu menu:menus){
            //url不为空，并且包含path
            if(!StringUtils.isEmpty(menu.getUrl())&& menu.getUrl().contains(path)){
                return true;
            }
            //子菜单递归
            if(menu.getChildren().size()>0){
                if(check(menu.getChildren(),path)) return true;
            }
        }
        return false;
    }
}
