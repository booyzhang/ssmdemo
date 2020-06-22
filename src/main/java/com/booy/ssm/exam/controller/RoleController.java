package com.booy.ssm.exam.controller;

import com.booy.ssm.exam.pojo.Menu;
import com.booy.ssm.exam.pojo.Role;
import com.booy.ssm.exam.service.MenuService;
import com.booy.ssm.exam.service.RoleService;
import com.booy.ssm.exam.utils.AjaxResult;
import com.booy.ssm.exam.utils.TableData;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/sys/role.html")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;
    @RequestMapping
    public String user(){
        return "role";
    }

    @RequestMapping(params = "act=table")
    @ResponseBody
    public TableData<Role> role(Role role, int page,int limit){
        PageInfo<Role> roleList = roleService.getRoleList(role,page, limit);
        TableData<Role> tableData = new TableData<>(roleList.getTotal(), roleList.getList());
        return tableData;
    }

    @RequestMapping(params = "act=tree")
    @ResponseBody
    public List<Menu> MenuTree(){
        return menuService.getMenuTree(true);
    }

    @RequestMapping(params = "act=assign")
    @ResponseBody
    public AjaxResult assign(Integer roleId, Integer[] menuIds){
        AjaxResult result = new AjaxResult();
        try {
            roleService.addRoleMenu(roleId,menuIds);
            result.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMessage("授权失败！");
        }
        return result;
    }
    @RequestMapping(params = "act=menuIds")
    @ResponseBody
    public List<Integer> menuIds(Integer roleId){
        List<Integer> menuIds = roleService.getMenuByRoleId(roleId);
        return menuIds;
    }
    @RequestMapping(params = "act=edit")
    @ResponseBody
    public AjaxResult edit(Role role){
        AjaxResult result = new AjaxResult();
        try {
            if(role.getId()==null){
                roleService.addRole(role);
                result.setStatus(true);
            }else{
                roleService.updateRole(role);
                result.setStatus(true);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMessage("编辑失败！");
            return result;
        }
    }

    @RequestMapping(params = "act=delete")
    @ResponseBody
    public AjaxResult deleteRole(Integer[] roleIds){
        AjaxResult result = new AjaxResult();
        try {
            roleService.deleteRole(roleIds);
            result.setStatus(true);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMessage("删除失败！");
            return result;
        }
    }
}
