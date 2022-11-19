package com.soen387.dao;

import com.soen387.beans.Person;
import com.soen387.beans.Student;

public interface StudentDao {
    boolean checkIsStudent(long studentId);
    boolean insert(Student student);
}
