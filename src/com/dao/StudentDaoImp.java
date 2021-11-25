package com.dao;

import com.util.SqlConnect;

public class StudentDaoImp implements StudentDao {
    @Override
    public boolean resetMail(String mail, int id) {
        try {
            SqlConnect.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int state = SqlConnect.addUpdateDelete("UPDATE student SET mail = '" + mail +
                "' WHERE id = " + id + ";");
        SqlConnect.closeConn();
        if (state == 1) {
            System.out.println("更新：id=" + id + ",mail修改为：" + mail);
            return true;
        } else {
            System.out.println("执行的非更新语句");
            return false;
        }
    }

    @Override
    public boolean resetPhone(String phone, int id) {
        try {
            SqlConnect.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int state = SqlConnect.addUpdateDelete("UPDATE student SET phone = '" + phone +
                "' WHERE id = " + id + ";");
        SqlConnect.closeConn();
        if (state == 1) {
            System.out.println("更新：id=" + id + ",phone修改为：" + phone);
            return true;
        } else {
            System.out.println("执行的非更新语句");
            return false;
        }
    }

    @Override
    public boolean resetAddress(String address, int id) {
        try {
            SqlConnect.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int state =
                SqlConnect.addUpdateDelete("UPDATE student SET address = '" + address +
                        "' WHERE id = " + id + ";");
        SqlConnect.closeConn();
        if (state == 1) {
            System.out.println("更新：id=" + id + ",address修改为：" + address);
            return true;
        } else {
            System.out.println("执行的非更新语句");
            return false;
        }
    }
}
