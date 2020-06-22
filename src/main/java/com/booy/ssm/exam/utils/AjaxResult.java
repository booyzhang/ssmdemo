package com.booy.ssm.exam.utils;

import lombok.Data;

@Data
public class AjaxResult {
    private boolean status;//是否成功
    private String message;//提示信息
    private Object result;//成功信息

    public AjaxResult() {
    }

    public AjaxResult(boolean status, String message, Object result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }
}
