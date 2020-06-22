package com.booy.ssm.exam.dao;

import com.booy.ssm.exam.pojo.Menu;

import java.util.List;

public interface MenuDAO {
    List<Menu> getAllMenu();
    void addMenu(Menu menu);
    void updateMenu(Menu menu);
    void deleteMenu(Integer id);
}
