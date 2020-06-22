package com.booy.ssm.exam.utils;

import org.springframework.util.DigestUtils;

public class MD5Utils {
    //添加时生成密码
    public static String getDigestMD5(String account, String password){
        String salt = DigestUtils.md5DigestAsHex(account.getBytes());
        return DigestUtils.md5DigestAsHex((salt + password).getBytes());
    }
    //登录时的密码
    public static String getLoginMD5(String salt, String password){
        return DigestUtils.md5DigestAsHex((salt + password).getBytes());
    }
}
