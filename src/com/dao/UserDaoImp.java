package com.dao;

import com.entity.Student;
import com.entity.Teacher;
import com.entity.User;
import com.util.SqlConnect;
import jakarta.servlet.ServletOutputStream;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Administrator
 */
public class UserDaoImp implements UserDao {
    private static final int TEACHER = 1;
    private static final int STUDENT = 0;

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

    @Override
    public boolean createUser(User user) {
        try {
            SqlConnect.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean flag = true;
        if (user.getIdentity() == STUDENT) {
            Student student = (Student) user;
            int state = SqlConnect.addUpdateDelete("INSERT INTO user ( account, " +
                    "password, identity ) VALUES ( '" + student.getAccount() + "', '" +
                    student.getPassword() + "', 0 );");
            if (state == 1) {
                System.out.println("插入user表：account=" + student.getAccount() + ", " +
                        "password=" + student.getPassword() + ", identity=0");
            } else {
                flag = false;
                System.out.println("执行错误的SQL语句为：" + "INSERT INTO user ( account, " +
                        "password, identity ) VALUES ( '" + student.getAccount() + "', '" +
                        student.getPassword() + "', 0 );");
            }
            ResultSet rs = SqlConnect.selectSql("SELECT id FROM user WHERE account = '" +
                    student.getAccount() + "' AND password = '" + student.getPassword() +
                    "' AND identity = 0;");
            try {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    rs = SqlConnect.selectSql("SELECT schoolID FROM student order by id" +
                            " desc " +
                            "limit 1;");
                    rs.next();
                    int schoolId = rs.getInt(1) + 1;
                    String s = "INSERT INTO student ( id, " +
                            "name, schoolID, major_id, year, class_id, birthday) VALUES" +
                            " (" + id + ", '" + student.getName() + "', '" + schoolId + "', " +
                            student.getMajorId() + ", " +
                            student.getYear() + ", " + student.getClassId() + ", '" +
                            student.getBirthday() + "');";
                    System.out.println(s);
                    state = SqlConnect.addUpdateDelete(s);
                    if (state == 1) {
                        System.out.println("插入student表：id=" + id + ", name=" + student.getName()
                                + ", major_id=" + student.getMajorId() + ", year=" + student.getYear()
                                + ", class_id=" + student.getClassId() + ", birthday=" +
                                student.getBirthday());
                    } else {
                        flag = false;
                    }
                } else {
                    flag = false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return flag;
        } else if (user.getIdentity() == TEACHER) {
            Teacher teacher = (Teacher) user;
            int state = SqlConnect.addUpdateDelete("INSERT INTO user ( account, " +
                    "password, identity ) VALUES ( '" + teacher.getAccount() + "', '" +
                    teacher.getPassword() + "', 1 );");
            if (state == 1) {
                System.out.println("插入user表：account=" + teacher.getAccount() + ", " +
                        "password=" + teacher.getPassword() + ", identity=0");
            } else {
                flag = false;
                System.out.println("执行错误的SQL语句为：" + "INSERT INTO user ( account, " +
                        "password, identity ) VALUES ( '" + teacher.getAccount() + "', '" +
                        teacher.getPassword() + "', 1 );");
            }
            ResultSet rs = SqlConnect.selectSql("SELECT id FROM user WHERE account = '" +
                    teacher.getAccount() + "' AND password = '" + teacher.getPassword() +
                    "' AND identity = 1;");
            try {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    rs = SqlConnect.selectSql("SELECT schoolID FROM teacher order by id" +
                            " desc " +
                            "limit 1;");
                    rs.next();
                    int schoolId = rs.getInt(1) + 1;
                    state = SqlConnect.addUpdateDelete("INSERT INTO teacher ( id, " +
                            "schoolID, name ) VALUES (" + id + ", '" + schoolId +
                            "', '" + teacher.getName() + "');");
                    if (state == 1) {
                        System.out.println("插入teacher表：id=" + id + ", name=" + teacher.getName());
                    } else {
                        flag = false;
                    }
                } else {
                    flag = false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return flag;
        } else {
            return false;
        }
    }
}
