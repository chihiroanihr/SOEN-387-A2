package com.soen387.dao;

import com.soen387.beans.CourseByAdmin;
import com.soen387.beans.Person;
import com.soen387.beans.Student;
import com.soen387.beans.StudentCourseEnrolled;

import java.util.List;

public interface StudentCourseEnrolledDao {
    public boolean delete(StudentCourseEnrolled studentCourseEnrolled);
    public boolean insert(StudentCourseEnrolled studentCourseEnrolled);
    public boolean deleteAll(long adminId, String courseCode, String semester);
    public List<Student> findAllStudentID(long adminId, String courseCode, String semester);
    List<Person> findAllStudentInfo(long adminId, String courseCode, String semester);
    List<CourseByAdmin> findAllCoursesEnrolledByStudent(long studentId);
    List<CourseByAdmin> findAllCoursesAvailableForStudent(long studentId);
    public boolean checkClassTimeOverlap(Student student, CourseByAdmin courseByAdmin);
}
