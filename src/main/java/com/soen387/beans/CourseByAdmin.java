package com.soen387.beans;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class CourseByAdmin implements Serializable {
    private static final long serialVersionUID = 1;
    private long adminID;
    private String courseCode;
    private String courseTitle;
    private String semester;
    private String daysOfWeek;
    private Time startTime;
    private Time endTime;
    private String room;
    private Date startDate;
    private Date endDate;

    // Constructor
    public CourseByAdmin(long adminID, String courseCode, String courseTitle, String semester, String daysOfWeek, Time startTime, Time endTime, String room, Date startDate, Date endDate) {
        this.adminID = adminID;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.semester = semester;
        this.daysOfWeek = daysOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Accessors and Mutators
    public long getAdminID() {
        return this.adminID;
    }

    public void setAdminID(long adminID) {
        this.adminID = adminID;
    }

    public String getCourseCode() {
        return this.courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return this.courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getSemester() {
        return this.semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDaysOfWeek() {
        return this.daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public Time getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getRoom() {
        return this.room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}