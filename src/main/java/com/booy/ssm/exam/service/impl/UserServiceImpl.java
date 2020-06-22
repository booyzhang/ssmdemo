package com.booy.ssm.exam.service.impl;

import com.booy.ssm.exam.dao.PremissionDAO;
import com.booy.ssm.exam.dao.UserDAO;
import com.booy.ssm.exam.pojo.User;
import com.booy.ssm.exam.service.UserService;
import com.booy.ssm.exam.utils.AjaxResult;
import com.booy.ssm.exam.utils.ExamConstants;
import com.booy.ssm.exam.utils.MD5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PremissionDAO premissionDAO;

    @Override
    public User dologin(String account, String password) {
        User user = userDAO.getUserByAccount(account);
        String loginMD5 = MD5Utils.getLoginMD5(user.getSalt(), password);
        if(user==null || !user.getPassword().equals(loginMD5) || user.getStatus().equals(ExamConstants.USER_STATUS_DELETE)){
            return null;//账号或密码错误
        }
        return user;
    }

    @Override
    public AjaxResult addUser(User user) {
        AjaxResult result = new AjaxResult();
        if(userDAO.getUserByAccount(user.getAccount())==null ){
            //设置用户动态盐
            user.setSalt(DigestUtils.md5DigestAsHex(user.getAccount().getBytes()));
            //加密密码
            user.setPassword(MD5Utils.getDigestMD5(user.getAccount(), user.getPassword()));
            userDAO.addUser(user);
            result.setStatus(true);
            return result;
        }
        result.setStatus(false);
        result.setMessage("用户名已存在！");
        return result;
    }

    @Override
    public AjaxResult updateUser(User user) {
        AjaxResult result = new AjaxResult();
        try {
            user.setPassword(MD5Utils.getDigestMD5(user.getAccount(), user.getPassword()));
            userDAO.updateUser(user);
            result.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMessage("更新失败！");
        }
        return result;
    }

    @Override
    public PageInfo<User> getUserList(User user, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<User> users = new PageInfo<>(userDAO.getUserList(user));
        return users;
    }

    @Override
    public AjaxResult deleteUser(int[] ids) {
        AjaxResult result = new AjaxResult();
        for(int id:ids){
            userDAO.deleteUser(id);
        }
        result.setStatus(true);
        return result;
    }

    //用户角色功能

    @Override
    public void addUserRole(Integer userId, Integer[] roleIds) {
        premissionDAO.deleteUserRoleByUserId(userId);
        for(Integer roleId:roleIds){
            premissionDAO.addUserRole(userId,roleId);
        }
    }
    @Override
    public List<Integer> getRoleByUserId(Integer userId) {
        return premissionDAO.getRoleByUserId(userId);
    }
}
