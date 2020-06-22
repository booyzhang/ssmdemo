package com.booy.ssm.exam.dao;

import com.booy.ssm.exam.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PremissionDAO {
    void deleteRoleMenuByRoleId(Integer roleId);
    void addRoleMenu(@Param("roleId") Integer roleId,@Param("menuId") Integer menuId);

    List<Integer> getMenuByRoleId(Integer roleId);

    void deleteUserRoleByUserId(Integer userId);
    void addUserRole(@Param("userId") Integer userId,@Param("roleId") Integer roleId);

    List<Integer> getRoleByUserId(Integer userId);

    List<Menu> getUserMenuList(Integer userId);
}
