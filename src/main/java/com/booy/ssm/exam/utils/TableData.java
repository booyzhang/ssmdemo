package com.booy.ssm.exam.utils;

import lombok.Data;

import java.util.List;

//数据封装
@Data
public class TableData<T> {
    private int code=0;
    private String msg="";
    private long count;
    private List<T> data;

    public TableData() {
    }

    public TableData(long count, List<T> data) {
        this.count = count;
        this.data = data;
    }
}
