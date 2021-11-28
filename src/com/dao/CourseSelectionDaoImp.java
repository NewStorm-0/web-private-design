package com.dao;

import com.entity.Course;
import com.entity.CourseSelection;
import com.util.SqlConnect;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
