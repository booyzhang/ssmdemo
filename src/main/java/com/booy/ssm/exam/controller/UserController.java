package com.booy.ssm.exam.controller;

import com.booy.ssm.exam.pojo.Role;
import com.booy.ssm.exam.pojo.User;
import com.booy.ssm.exam.service.RoleService;
import com.booy.ssm.exam.service.UserService;
import com.booy.ssm.exam.utils.AjaxResult;
import com.booy.ssm.exam.utils.TableData;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/sys/user.html")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping
    public String user(){
        return "user";
    }


    @RequestMapping(params = "act=table")
    @ResponseBody
    public TableData table(User user,int page,int limit){
        PageInfo<User> pageInfo = userService.getUserList(user, page, limit);
        TableData tableData = new TableData(pageInfo.getTotal(), pageInfo.getList());
        return tableData;
    }
    @RequestMapping(params = "act=edit")
    @ResponseBody
    public AjaxResult edit(User user){
        //添加
        if(user.getId()==null){
            AjaxResult result = userService.addUser(user);
            return result;
        }else{//修改
            AjaxResult result = userService.updateUser(user);
            return result;
        }
    }
    @RequestMapping(params = "act=delete")
    @ResponseBody
    public AjaxResult deleteUser(int[] ids){
        try {
            AjaxResult result = userService.deleteUser(ids);
            result.setStatus(true);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            AjaxResult result = new AjaxResult();
            result.setStatus(false);
            result.setMessage("删除失败！");
            return result;
        }
    }

    @RequestMapping(params = "act=roleTree")
    @ResponseBody
    public List<Role> roleTree(){
        return roleService.getRoleList();
    }

    @RequestMapping(params = "act=assign")
    @ResponseBody
    public AjaxResult assign(Integer userId, Integer[] roleIds){
        AjaxResult result = new AjaxResult();
        try {
            userService.addUserRole(userId,roleIds);
            result.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMessage("授权失败！");
        }
        return result;
    }

    @RequestMapping(params = "act=roleIds")
    @ResponseBody
    public List<Integer> menuIds(Integer userId){
        List<Integer> roleIds = userService.getRoleByUserId(userId);
        return roleIds;
    }
}
