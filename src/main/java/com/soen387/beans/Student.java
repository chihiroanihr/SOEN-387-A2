package com.soen387.beans;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1;
    private long studentId;

    // Constructor
    public Student(long studentId) {
        this.studentId = studentId;
    }

    // Accessors
    public long getStudentId() {
        return studentId;
    }

    // Mutators
    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }
}
