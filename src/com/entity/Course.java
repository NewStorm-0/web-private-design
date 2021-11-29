package com.entity;

import java.sql.Date;

/**
 * @author Administrator
 */
public class Course {
    private int id;
    private String courseName;
    private int teacherId;
    private int credit;
    private String description;
    private Date selectionDeadline;
    private String day;
    private String time;

    public Course(int id, String courseName, int teacherId, int credit,
                  Date selectionDeadline, String day, String time) {
        this.id = id;
        this.courseName = courseName;
        this.teacherId = teacherId;
        this.credit = credit;
        this.selectionDeadline = selectionDeadline;
        this.day = day;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getSelectionDeadline() {
        return selectionDeadline;
    }

    public void setSelectionDeadline(Date selectionDeadline) {
        this.selectionDeadline = selectionDeadline;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
