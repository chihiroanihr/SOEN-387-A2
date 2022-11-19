package com.soen387.beans;

import java.io.Serializable;

public class Admin implements Serializable {
    private static final long serialVersionUID = 1;
    private long adminId;

    // Constructor
    public Admin(long adminId) {
        this.adminId = adminId;
    }

    // Accessors
    public long getAdminId() {
        return adminId;
    }

    // Mutators
    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }
}