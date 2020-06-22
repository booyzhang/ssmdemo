package com.booy.ssm.exam.dao;

import com.booy.ssm.exam.pojo.Role;

import java.util.List;

public interface RoleDAO {
    List<Role>  getRoleList();
    List<Role> getRoleListByIF(Role role);
    void addRole(Role role);
    void updateRole(Role role);
    void deleteRole(Integer id);
}
