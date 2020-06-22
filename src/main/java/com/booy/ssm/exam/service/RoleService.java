package com.booy.ssm.exam.service;

import com.booy.ssm.exam.pojo.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {
    //角色列表
    PageInfo<Role> getRoleList(Role role, int pageNum,int pageSize);
    //获取所有角色,角色树用
    List<Role> getRoleList();
    //增删改
    void addRole(Role role);
    void updateRole(Role role);
    void deleteRole(Integer[] roleIds);
    //角色菜单功能
    void addRoleMenu(Integer roleId,Integer[] MenuIds);
    List<Integer> getMenuByRoleId(Integer roleId);
}
