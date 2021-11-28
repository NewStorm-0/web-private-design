package com.dao;

import com.entity.Course;

import java.util.List;

public interface CourseDao {
    List<Course> getAllCourses(int teacherId);
    boolean addCourse(Course course);
    Course getSingleCourse(int courseId);
}
