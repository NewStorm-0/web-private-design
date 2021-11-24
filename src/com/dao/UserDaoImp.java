package com.dao;

import com.entity.Teacher;
import com.entity.User;
import com.util.SqlConnect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImp implements UserDao {
    @Override
    public User login(String account, String password, int identity) {
        try {
            try {
                SqlConnect.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ResultSet resultSet = SqlConnect.selectSql("SELECT * FROM user WHERE account = '"
                    + account + "' AND password = '" + password + "' AND identity = '"
                    + identity + "';");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                System.out.println("登录的用户id: " + id);
                if (identity == 1) {
                    return null;
                } else {
                    resultSet = SqlConnect.selectSql("SELECT * FROM teacher WHERE id = " + id + ";");
                    if (resultSet.next()) {
                        String schoolId = resultSet.getString(2);
                        String name = resultSet.getString(3);
                        System.out.println("schoolId: " + schoolId + "; name: " + name);
                        return new Teacher(id, account, password, schoolId, name);
                    } else {
                        System.out.println("teacher表可能有问题");
                        return null;
                    }
                }
            }
            SqlConnect.closeConn();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean register(User user) {
        return false;
    }

    @Override
    public List<User> getUserAll() {
        return null;
    }

    @Override
    public boolean resetPassword(String oldKey, String newKey) {
        return false;
    }
}
