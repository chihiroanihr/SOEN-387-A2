package com.soen387.beans;

import java.io.Serializable;

public class StudentCourseEnrolled implements Serializable {
    private static final long serialVersionUID = 1;
    private long studentID;
    private String courseCode;
    private String semester;
    private long adminID;

    // Constructor
    public StudentCourseEnrolled(long studentID, String courseCode, String semester, long adminID) {
        this.studentID = studentID;
        this.courseCode = courseCode;
        this.semester = semester;
        this.adminID = adminID;
    }

    // Accessors and Mutators
    public long getStudentID() {
        return this.studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public String getCourseCode() {
        return this.courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getSemester() {
        return this.semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public long getAdminID() {
        return this.adminID;
    }

    public void setAdminID(long adminID) {
        this.adminID = adminID;
    }
}