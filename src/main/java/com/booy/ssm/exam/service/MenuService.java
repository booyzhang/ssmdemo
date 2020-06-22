package com.booy.ssm.exam.service;

import com.booy.ssm.exam.pojo.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getMenuTree(Boolean needButton);
    void addMenu(Menu menu);
    void updateMenu(Menu menu);
    void deleteMenu(int[] ids);

    List<Menu> getUserMenuList(Integer userId);
}
