package com.entity;

import com.dao.TeacherDao;

public class Teacher extends User {
    private String schoolId;
    private String name;

    public Teacher() {
    }

    public Teacher(int id, String account, String password, String schoolId, String name) {
        super(id, account, password, 1);
        this.schoolId = schoolId;
        this.name = name;
    }

    @Override
    public String toString() {
        return ("Teacher: id = " + id+ ", account=" + account +
                ", password=" + password + ", schoolId=" + schoolId +
                ", name=" + name);
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
