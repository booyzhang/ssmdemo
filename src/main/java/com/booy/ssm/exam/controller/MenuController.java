package com.booy.ssm.exam.controller;

import com.booy.ssm.exam.pojo.Menu;
import com.booy.ssm.exam.service.MenuService;
import com.booy.ssm.exam.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/sys/menu.html")
public class MenuController {
    @Autowired
    MenuService menuService;
    @RequestMapping
    public String user(){
        return "menu";
    }

    @RequestMapping(params = "act=tree")
    @ResponseBody
    public List<Menu> MenuTree(Boolean needButton){
        return menuService.getMenuTree(true);
    }

    @RequestMapping(params = "act=edit")
    @ResponseBody
    public AjaxResult edit(Menu menu){
        AjaxResult result = new AjaxResult();
        //添加功能
        try {
            if(menu.getId()==null){
                menuService.addMenu(menu);
            }else{
                menuService.updateMenu(menu);
            }
            result.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMessage("编辑失败！");
        }
        return result;
    }
    @RequestMapping(params = "act=delete")
    @ResponseBody
    public AjaxResult deleteMenu(int[] ids){
        AjaxResult result = new AjaxResult();
        try {
            menuService.deleteMenu(ids);
            result.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMessage("删除失败！");
        }
        return result;
    }
}
