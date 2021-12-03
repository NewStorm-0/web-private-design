package com.dao;

import com.entity.Course;
import com.entity.CourseSelection;
import com.util.SqlConnect;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class CourseSelectionDaoImp implements CourseSelectionDao {
    @Override
    public List<CourseSelection> getAllCourses(int studentId) {
        try {
            try {
                SqlConnect.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ResultSet resultSet =
                    SqlConnect.selectSql("SELECT * FROM course_selection Where " +
                            "student_id = " +
                            studentId + ";");
            List<CourseSelection> list = new ArrayList<>();
            while (resultSet.next()) {
                CourseSelection courseSelection = null;
                int id = resultSet.getInt(1);
                int courseId = resultSet.getInt(3);
                int studentGrade = resultSet.getInt(4);
                courseSelection = new CourseSelection(id, studentId, courseId);
                courseSelection.setStudentGrade(studentGrade);
                list.add(courseSelection);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isSelected(int studentId, int courseId) {
        try {
            try {
                SqlConnect.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ResultSet resultSet =
                    SqlConnect.selectSql("SELECT * FROM course_selection Where " +
                            "student_id = " +
                            studentId + " AND course_id = " + courseId + ";");
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean selectCourse(int studentId, int courseId) {
        try {
            SqlConnect.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int state =
                SqlConnect.addUpdateDelete("INSERT INTO course_selection ( student_id, " +
                        "course_id ) VALUES ( " + studentId + ", "
                        + courseId + " );");
        SqlConnect.closeConn();
        if (state == 1) {
            System.out.println("插入 course_selection表：studentId=" + studentId + ", " +
                    "course_id=" + courseId);
            return true;
        } else {
            System.out.println("CourseSelectionDaoImp.selectCourse执行语句错误");
            System.out.println("错误SQL语句为：" + "INSERT INTO course_selection ( student_id, " +
                    "course_id ) VALUES ( " + studentId + ", "
                    + courseId + " );");
            return false;
        }
    }

    @Override
    public boolean dropCourse(int studentId, int courseId) {
        try {
            SqlConnect.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sqlStatement =
                "DELETE FROM course_selection WHERE student_id = " + studentId +
                        " AND course_id = " + courseId + ";";
        int state =
                SqlConnect.addUpdateDelete(sqlStatement);
        SqlConnect.closeConn();
        if (state == 1) {
            System.out.println("删除 course_selection表：studentId=" + studentId + ", " +
                    "course_id=" + courseId);
            return true;
        } else {
            System.out.println("CourseSelectionDaoImp.dropCourse执行语句错误");
            System.out.println("错误SQL语句为：" + sqlStatement);
            return false;
        }
    }
}
