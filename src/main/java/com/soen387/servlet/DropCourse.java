package com.soen387.servlet;

import com.google.gson.Gson;
import com.soen387.beans.CourseByAdmin;
import com.soen387.beans.Student;
import com.soen387.beans.StudentCourseEnrolled;
import com.soen387.dao.StudentCourseEnrolledDao;
import com.soen387.daoImpl.StudentCourseEnrolledDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet(name = "dropCourse", value = "/dropCourse")
public class DropCourse extends HttpServlet {
    private static StudentCourseEnrolledDao studentCourseEnrolledDao;
    private static PrintWriter out;

    public void init() {
        studentCourseEnrolledDao = new StudentCourseEnrolledDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Prepare output
        out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, String> responseData = new LinkedHashMap<>();

        // Extract request parameters
        long studentId = Long.parseLong(request.getParameter("userId"));

        // Fetch all the courses enrolled by the given student ID
        List<CourseByAdmin> coursesByStudent = studentCourseEnrolledDao.findAllCoursesEnrolledByStudent(studentId);

        // If student takes no courses
        if (coursesByStudent.isEmpty()) {
            responseData.put("status", "empty");
        }
        // If student takes courses
        else {
            String coursesJson = new Gson().toJson(coursesByStudent);
            responseData.put("status", "success");
            responseData.put("courses", coursesJson);
        }

        // Output
        String json = new Gson().toJson(responseData);
        response.getWriter().write(json);

        // Close output
        out.flush();
        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Prepare output
        out = response.getWriter();

        // Extract request parameters
        long studentId = Long.parseLong(request.getParameter("userId"));
        long adminId = Long.parseLong(request.getParameter("adminID"));
        String courseCode = request.getParameter("courseCode");
        String semester = request.getParameter("semester");

        // Create instance
        StudentCourseEnrolled studentCourseEnrolled = new StudentCourseEnrolled(studentId, courseCode, semester, adminId);
//        Student student = new Student(studentId);
//        CourseByAdmin course = new CourseByAdmin(adminId, courseCode, courseTitle, semester, daysOfWeek, startTime, endTime, room, startDate, endDate);

        // If class date time overlaps with other classes created by admin
//        if (studentCourseEnrolledDao.checkClassTimeOverlap(student, course)) {
//            out.print("error-overlap");
//        }
//        // Insert success
//        else
        if (studentCourseEnrolledDao.delete(studentCourseEnrolled)) {
            out.print("success");
        }
        // Insert fails due to sql integrity error
        else {
            out.print("error-sql");
        }

        // Close output
        out.flush();
        out.close();
    }

}
