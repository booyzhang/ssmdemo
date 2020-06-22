package com.booy.ssm.exam.dao;

import com.booy.ssm.exam.pojo.User;

import java.util.List;

public interface UserDAO {
    User getUserByAccount(String account);
    void addUser(User user);
    void updateUser(User user);
    List<User> getUserList(User user);
    void deleteUser(int id);
}
