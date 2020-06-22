package com.booy.ssm.exam.service;

import com.booy.ssm.exam.pojo.User;
import com.booy.ssm.exam.utils.AjaxResult;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface UserService {
    //用户登录
    User dologin(String account,String password);
    AjaxResult addUser(User user);
    AjaxResult updateUser(User user);
    PageInfo<User> getUserList(User user,int pageNum,int pageSize);
    AjaxResult deleteUser(int[] ids);

    //用户角色功能
    void addUserRole(Integer userId,Integer[] roleIds);
    List<Integer> getRoleByUserId(Integer userId);

}
