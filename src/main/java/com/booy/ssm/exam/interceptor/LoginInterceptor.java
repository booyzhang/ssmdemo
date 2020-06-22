package com.booy.ssm.exam.interceptor;

import com.booy.ssm.exam.utils.ExamConstants;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute(ExamConstants.SESSION_USER)==null){
            response.sendRedirect("/login.html");
            return false;
        }
        return true;
    }
}
