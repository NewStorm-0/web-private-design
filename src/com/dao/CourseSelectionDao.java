package com.dao;

import com.entity.CourseSelection;

import java.util.List;

public interface CourseSelectionDao {
    List<CourseSelection> getAllCourses(int studentId);
    boolean isSelected(int studentId, int courseId);
}
