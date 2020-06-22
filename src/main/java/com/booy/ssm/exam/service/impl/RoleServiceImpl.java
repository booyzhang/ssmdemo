package com.booy.ssm.exam.service.impl;

import com.booy.ssm.exam.dao.PremissionDAO;
import com.booy.ssm.exam.dao.RoleDAO;
import com.booy.ssm.exam.pojo.Menu;
import com.booy.ssm.exam.pojo.Role;
import com.booy.ssm.exam.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private PremissionDAO premissionDAO;

    @Override
    public PageInfo<Role> getRoleList(Role role,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Role> roles = new PageInfo<>(roleDAO.getRoleListByIF(role));
        return roles;
    }
//重改方法名
    @Override
    public List<Role> getRoleList() {
       return roleDAO.getRoleList();
    }
//增删改查

    @Override
    public void addRole(Role role) {
        roleDAO.addRole(role);
    }

    @Override
    public void updateRole(Role role) {
        roleDAO.updateRole(role);
    }

    @Override
    public void deleteRole(Integer[] roleIds) {
        for(Integer roleId:roleIds){
            roleDAO.deleteRole(roleId);
        }
    }

    @Override
    public void addRoleMenu(Integer roleId, Integer[] MenuIds) {
        premissionDAO.deleteRoleMenuByRoleId(roleId);
        for(int menu:MenuIds){
            premissionDAO.addRoleMenu(roleId,menu);
        }
    }

    @Override
    public List<Integer> getMenuByRoleId(Integer roleId) {
        List<Integer> menuIds = premissionDAO.getMenuByRoleId(roleId);
        return menuIds;
    }
}
