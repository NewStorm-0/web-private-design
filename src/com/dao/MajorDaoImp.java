package com.dao;

import com.entity.Class;
import com.entity.Major;
import com.util.SqlConnect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class MajorDaoImp implements MajorDao{
    @Override
    public List<Integer> getStudentsId(int majorId) {
        try {
            try {
                SqlConnect.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ResultSet resultSet =
                    SqlConnect.selectSql("SELECT * FROM student Where major_id = " +
                            majorId + ";");
            List<Integer> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getInt(1));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Major> getMajorsList() {
        try {
            try {
                SqlConnect.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ResultSet resultSet =
                    SqlConnect.selectSql("SELECT * FROM major;");
            List<Major> list = new ArrayList<>();
            while (resultSet.next()) {
                Major major = new Major(resultSet.getInt(1), resultSet.getString(2));
                list.add(major);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
