package com.soen387.dao;

import com.soen387.beans.CourseByAdmin;

import java.util.List;

public interface CourseByAdminDao {
    List<CourseByAdmin> findAllCoursesByAdmin(long adminId);

    public boolean delete(long adminId, String courseCode, String semester);

    boolean insert(CourseByAdmin courseByAdmin);

    boolean update(CourseByAdmin courseByAdmin);

    boolean checkClassTimeOverlap(CourseByAdmin courseByAdmin);
}
