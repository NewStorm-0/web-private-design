package com.dao;

import java.sql.Date;

/**
 * @author Administrator
 */
public interface StudentDao {
    boolean resetMail(String mail, int id);
    boolean resetPhone(String phone, int id);
    boolean resetAddress(String address, int id);
    String getMajor(int id);
    String getStudentClass(int id);
    String getSchoolId(int id);
    String getName(int id);
    String getMajorFromStudent(int studentId);
    String getClassFromStudent(int studentId);
    int getClassId(int studentId);
    int getMajorId(int studentId);
}
