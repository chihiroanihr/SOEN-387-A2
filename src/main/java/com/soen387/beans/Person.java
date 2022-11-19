package com.soen387.beans;

import java.io.Serializable;
import java.sql.Date;

public class Person implements Serializable {
    private static final long serialVersionUID = 1;
    private long personId;
    private String password;
    private String lastName;
    private String firstName;
    private Date dob;
    private String email;
    private long phoneNum;
    private String address;

    // Constructor
    public Person(long personId, String password, String firstName, String lastName, Date dob, String email, long phoneNum, String address) {
        this.personId = personId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
    }

    // Accessors
    public long getPersonId() {
        return personId;
    }

    // Mutators
    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
