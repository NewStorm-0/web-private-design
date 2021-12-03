package com.dao;

import com.entity.Course;

import java.util.List;

public interface CourseDao {
    List<Course> getAllCourses(int teacherId);
    Course getSingleCourse(int courseId);
    List<Course> getOptionalCourses();
    boolean addCourse(Course course);
}
