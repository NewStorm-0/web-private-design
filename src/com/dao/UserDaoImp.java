package com.dao;

import com.entity.Student;
import com.entity.Teacher;
import com.entity.User;
import com.util.SqlConnect;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Administrator
 */
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
                    + account + "' AND password = '" + password + "' AND identity = "
                    + identity + ";");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                System.out.println("登录的用户id: " + id);
                if (identity == 0) {
                    resultSet = SqlConnect.selectSql("SELECT * FROM student WHERE id = " + id + ";");
                    if (resultSet.next()) {
                        String schoolId = resultSet.getString(3);
                        String name = resultSet.getString(2);
                        int majorId = resultSet.getInt(4);
                        int year = resultSet.getInt(5);
                        int classId = resultSet.getInt(6);
                        String phone = resultSet.getString(7);
                        Date birthday = resultSet.getDate(8);
                        String mail = resultSet.getString(9);
                        String address = resultSet.getString(10);
                        Student student = new Student(id, account, password, schoolId, name);
                        student.setMajorId(majorId);
                        student.setYear(year);
                        student.setClassId(classId);
                        student.setPhone(phone);
                        student.setBirthday(birthday);
                        student.setMail(mail);
                        student.setAddress(address);
                        System.out.println(student);
                        return student;
                    } else {
                        System.out.println("student表可能有问题");
                        return null;
                    }
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
    public boolean resetPassword(String password, int id) {
        try {
            SqlConnect.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int state = SqlConnect.addUpdateDelete("UPDATE user SET password = '" +
                password + "' WHERE id = " + id + ";");
        SqlConnect.closeConn();
        if (state == 1) {
            System.out.println("更新：id=" + id + ",password修改为：" + password);
            return true;
        } else {
            System.out.println("执行的非更新语句");
            return false;
        }
    }

    @Override
    public boolean resetAccount(String account, int id) {
        try {
            SqlConnect.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int state = SqlConnect.addUpdateDelete("UPDATE user SET account = '" +
                account + "' WHERE id = " + id + ";");
        SqlConnect.closeConn();
        if (state == 1) {
            System.out.println("更新：id=" + id + ",account修改为：" + account);
            return true;
        } else {
            System.out.println("执行的非更新语句");
            return false;
        }
    }

    @Override
    public boolean register(User user) {
        return false;
    }

    @Override
    public List<User> getUserAll() {
        return null;
    }
}
