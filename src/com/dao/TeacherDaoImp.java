package com.dao;

import com.util.SqlConnect;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Administrator
 */
public class TeacherDaoImp implements TeacherDao {
    @Override
    public boolean resetName(String name, int id) {
        try {
            SqlConnect.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int state = SqlConnect.addUpdateDelete("UPDATE teacher SET name = '" + name
                + "' WHERE id = " + id + ";");
        SqlConnect.closeConn();
        if (state == 1) {
            System.out.println("更新：id=" + id + ",name修改为：" + name);
            return true;
        } else {
            System.out.println("执行的非更新语句");
            return false;
        }
    }
}
