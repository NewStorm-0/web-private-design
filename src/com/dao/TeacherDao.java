package com.dao;

import com.entity.Teacher;

/**
 * @author Administrator
 */
public interface TeacherDao {
    boolean resetName(String name, int id);
    String getTeacherName(int id);
}
