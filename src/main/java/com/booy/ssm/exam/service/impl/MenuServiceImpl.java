package com.booy.ssm.exam.service.impl;

import com.booy.ssm.exam.dao.MenuDAO;
import com.booy.ssm.exam.dao.PremissionDAO;
import com.booy.ssm.exam.pojo.Menu;
import com.booy.ssm.exam.service.MenuService;
import com.booy.ssm.exam.utils.ExamConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDAO menuDAO;

    @Autowired
    private PremissionDAO premissionDAO;

    @Override
    public List<Menu> getMenuTree(Boolean needButton) {
        //所有菜单对象
        List<Menu> menus = menuDAO.getAllMenu();
        return makeMenuTree(needButton,menus);
    }
    //用户权限的菜单树对象
    @Override
    public List<Menu> getUserMenuList(Integer userId) {
        List<Menu> menuList = makeMenuTree(false, premissionDAO.getUserMenuList(userId));

        return menuList;
    }

    //菜单树封装方法
    public List<Menu> makeMenuTree(Boolean needButton,List<Menu> menus){
        //一级菜单
        List<Menu> fistMenus=new ArrayList<>();
        //所有菜单键值对存储
        HashMap<Integer, Menu> menuMap = new HashMap<>();
        //遍历所有菜单对象，没有父节点存储到一级菜单列表，并且将所有菜单对象存储到map中
        for (Menu menu : menus) {
            if (menu.getParentId() == null) {
                fistMenus.add(menu);
            }
            menuMap.put(menu.getId(), menu);
        }
        //填充拼接菜单树，遍历所有菜单对象，有父节点存储到属性子节点列表
        for (Menu menu : menus){
            //不是一级菜单，并且map中有父节点,把当前菜单对象设置给父节点列表中
            if(menu.getParentId()!=null && menuMap.containsKey(menu.getParentId()) ){
                if(!needButton && menu.getType().equals(ExamConstants.MENU_TYPE_BUTTON)){
                    continue;
                }
                menuMap.get(menu.getParentId()).getChildren().add(menu);
            }
        }
        return fistMenus;
    }

    @Override
    public void addMenu(Menu menu) {
        menuDAO.addMenu(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuDAO.updateMenu(menu);
    }

    @Override
    public void deleteMenu(int[] ids) {
        for(int id:ids){
            menuDAO.deleteMenu(id);
        }
    }
}
