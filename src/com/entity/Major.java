package com.entity;

/**
 * @author Administrator
 */
public class Major {
    private int id;
    private String majorName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Major() {

    }

    public Major(int id, String majorName) {
        this.id = id;
        this.majorName = majorName;
    }

    @Override
    public String toString() {
        return "Major{" +
                "id=" + id +
                ", majorName='" + majorName + '\'' +
                '}';
    }
}
