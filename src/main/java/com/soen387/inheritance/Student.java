package com.soen387.inheritance;

import java.sql.Date;

public class Student extends Person {
    // Additional attributes for a student

    // Constructor
    public Student(long ID, String password, String firstName, String lastName, String address,
                   String email, long phoneNumber, Date dateOfBirth) {
        super(ID, password, firstName, lastName, address, email, phoneNumber, dateOfBirth);
    }

    public String getType() { return "student"; }
}

