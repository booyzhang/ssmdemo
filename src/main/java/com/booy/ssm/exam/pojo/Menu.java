package com.booy.ssm.exam.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menu {
    private Integer id;
    private String name;//菜单名
    private String url;
    private Integer parentId;//父id
    private Integer sort;//排序
    private Integer type;
    private List<Menu> children=new ArrayList<>();

    public String getText(){
        return this.name;
    }
}
