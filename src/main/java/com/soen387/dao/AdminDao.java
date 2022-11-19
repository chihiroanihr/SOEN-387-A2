package com.soen387.dao;

import com.soen387.beans.Admin;

public interface AdminDao {
    boolean checkIsAdmin(long adminId);
    boolean insert(Admin admin);
}
