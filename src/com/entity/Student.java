package com.entity;

import java.sql.Date;

/**
 * @author Administrator
 */
public class Student extends User {
    private String schoolId;
    private String name;
    //专业id
    private int majorId;
    //年级
    private int year;
    private int classId;
    private String phone;
    private Date birthday;
    private String mail;
    private String address;

    public Student() {
    }

    public Student(int id, String account, String password, String schoolId, String name) {
        super(id, account, password, 0);
        this.schoolId = schoolId;
        this.name = name;
    }

    @Override
    public String toString() {
        return ("Student: id=" + id + ", account=" + account + ", password=" +
                password + ", schoolId=" + schoolId + ", name=" + name +
                ", majorId=" + majorId + ", year=" + year + ", classId=" +
                classId + ", phone=" + phone + ", birthday" + birthday + ", mail=" +
                mail + ", address=" + address);
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

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
