package com.dao;

import com.entity.Course;
import com.util.SqlConnect;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImp implements CourseDao {
    @Override
    public List<Course> getAllCourses(int teacherId) {
        try {
            try {
                SqlConnect.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ResultSet resultSet =
                    SqlConnect.selectSql("SELECT * FROM course Where teacher_id = " +
                            teacherId + ";");
            List<Course> list = new ArrayList<>();
            while (resultSet.next()) {
                Course course = null;
                int courseId = resultSet.getInt(1);
                String courseName = resultSet.getString(2);
                int credit = resultSet.getInt(4);
                String description = resultSet.getString(5);
                Date selectionDeadline = resultSet.getDate(6);
                String day = resultSet.getString(7);
                String time = resultSet.getString(8);
                course = new Course(courseId, courseName, teacherId, credit,
                        selectionDeadline, day, time);
                course.setDescription(description);
                list.add(course);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addCourse(Course course) {
        return false;
    }

    @Override
    public Course getSingleCourse(int courseId) {
        try {
            try {
                SqlConnect.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ResultSet resultSet =
                    SqlConnect.selectSql("SELECT * FROM course Where course_id = " +
                            courseId + ";");
            while (resultSet.next()) {
                Course course = null;
                String courseName = resultSet.getString(2);
                int teacherId = resultSet.getInt(3);
                int credit = resultSet.getInt(4);
                String description = resultSet.getString(5);
                Date selectionDeadline = resultSet.getDate(6);
                String day = resultSet.getString(7);
                String time = resultSet.getString(8);
                course = new Course(courseId, courseName, teacherId, credit,
                        selectionDeadline, day, time);
                course.setDescription(description);
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Course> getOptionalCourses() {
        try {
            try {
                SqlConnect.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Date date = new Date(System.currentTimeMillis());
            ResultSet resultSet =
                    SqlConnect.selectSql("SELECT * FROM course Where " +
                            "course_selection_deadline > '" + date.toString() + "';");
            List<Course> list = new ArrayList<>();
            while (resultSet.next()) {
                Course course = null;
                int courseId = resultSet.getInt(1);
                String courseName = resultSet.getString(2);
                int credit = resultSet.getInt(4);
                String description = resultSet.getString(5);
                Date selectionDeadline = resultSet.getDate(6);
                String day = resultSet.getString(7);
                String time = resultSet.getString(8);
                course = new Course(courseId, courseName, 1, credit,
                        selectionDeadline, day, time);
                course.setDescription(description);
                list.add(course);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
