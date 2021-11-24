package com.dao;

import com.entity.User;

import java.util.List;

public interface UserDao {
    User login(String account, String password, int identity);
    boolean register(User user);
    List<User> getUserAll();
    boolean resetPassword(String oldKey, String newKey);
}
