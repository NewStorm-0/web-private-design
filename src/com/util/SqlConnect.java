package com.util;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;

public class SqlConnect {
    static String url = "jdbc:mysql://localhost:3306/school_system?useSSL=false";
    static String user = "root";
    static String password = "2456601397";
    static Connection conn = null;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultset = null;
    static Statement statement = null;

    public static void init() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("连接MySQL失败");
            e.printStackTrace();
        }
    }

    public static int addUpdateDelete(String sql) {
        int i = 0;
        try {
            preparedStatement = conn.prepareStatement(sql);
            boolean flag = preparedStatement.execute();
            if (!flag) {
                i++;
            }
        } catch (Exception e) {
            System.out.println("数据库增删改异常");
            e.printStackTrace();
        }
        return i;
    }

    public static ResultSet selectSql(String sql) {
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultset = preparedStatement.executeQuery();
        } catch (Exception e) {
            System.out.println("数据库查询异常");
            e.printStackTrace();
        }
        return resultset;
    }

    public static void closeConn() {
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println("数据库关闭异常");
            e.printStackTrace();
        }
    }
}
