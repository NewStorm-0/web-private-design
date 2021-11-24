package com.entity;

public class Teacher extends User {
    private String schoolId;
    private String name;

    public Teacher(int id, String account, String password, String schoolId, String name) {
        super(id, account, password, 2);
        this.schoolId = schoolId;
        this.name = name;
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
