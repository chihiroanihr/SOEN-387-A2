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

@WebServlet(name = "addCourse", value = "/addCourse")
public class AddCourse extends HttpServlet {
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
        Map<String, String> coursesByStudentLst = new LinkedHashMap<>();
        Map<String, String> coursesForStudentLst = new LinkedHashMap<>();

        // Extract request parameters
        long studentId = Long.parseLong(request.getParameter("userId"));

        // Fetch all the courses enrolled by the given student ID
        List<CourseByAdmin> coursesByStudent = studentCourseEnrolledDao.findAllCoursesEnrolledByStudent(studentId);

        // If student takes no courses
        if (coursesByStudent.isEmpty()) {
            coursesByStudentLst.put("status", "empty");
        }
        // If student takes courses
        else {
            String coursesJson = new Gson().toJson(coursesByStudent);
            coursesByStudentLst.put("status", "success");
            coursesByStudentLst.put("courses", coursesJson);
        }

        // Fetch all the courses available for the given student ID
        List<CourseByAdmin> coursesForStudent = studentCourseEnrolledDao.findAllCoursesAvailableForStudent(studentId);
        // If student takes no courses
        if (coursesForStudent.isEmpty()) {
            coursesForStudentLst.put("status", "empty");
        }
        // If student takes courses
        else {
            String coursesJson = new Gson().toJson(coursesForStudent);
            coursesForStudentLst.put("status", "success");
            coursesForStudentLst.put("courses", coursesJson);
        }

        // Output
        String coursesByStudentJson = new Gson().toJson(coursesByStudentLst);
        responseData.put("coursesByStudent", coursesByStudentJson);

        String coursesForStudentJson = new Gson().toJson(coursesForStudentLst);
        responseData.put("coursesForStudent", coursesForStudentJson);

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
        String courseTitle = request.getParameter("courseTitle");
        String semester = request.getParameter("semester");
        String daysOfWeek = Objects.equals(request.getParameter("daysOfWeek"), "")
                ? null
                : request.getParameter("daysOfWeek");
        Time startTime = Objects.equals(request.getParameter("startTime"), "")
                ? null
                : Time.valueOf(request.getParameter("startTime") + ":00");
        Time endTime = Objects.equals(request.getParameter("endTime"), "")
                ? null
                : Time.valueOf(request.getParameter("endTime") + ":00");
        String room = Objects.equals(request.getParameter("room"), "")
                ? null
                : request.getParameter("room");
        Date startDate = Objects.equals(request.getParameter("startDate"), "")
                ? null
                : Date.valueOf(request.getParameter("startDate"));
        Date endDate = Objects.equals(request.getParameter("endDate"), "")
                ? null
                : Date.valueOf(request.getParameter("endDate"));

        // Check admin

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
        if (studentCourseEnrolledDao.insert(studentCourseEnrolled)) {
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
