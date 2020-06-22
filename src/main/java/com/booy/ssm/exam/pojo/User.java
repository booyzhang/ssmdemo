package com.booy.ssm.exam.pojo;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String account;
    private String password;
    private String nickname;
    private Integer status;
    private String salt;
}
