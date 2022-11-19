package com.soen387.servlet;

import com.google.gson.Gson;
import com.soen387.beans.CourseByAdmin;
import com.soen387.dao.AdminDao;
import com.soen387.dao.CourseByAdminDao;
import com.soen387.dao.StudentCourseEnrolledDao;
import com.soen387.daoImpl.AdminDaoImpl;
import com.soen387.daoImpl.CourseByAdminDaoImpl;
import com.soen387.daoImpl.StudentCourseEnrolledDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "deleteCourse", value = "/deleteCourse")
public class DeleteCourse extends HttpServlet {
    private static AdminDao adminDao;
    private static CourseByAdminDao courseByAdminDao;
    private static StudentCourseEnrolledDao studentCourseEnrolledDao;
    private static PrintWriter out;

    public void init() {
        adminDao = new AdminDaoImpl();
        courseByAdminDao = new CourseByAdminDaoImpl();
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
        long adminId = Long.parseLong(request.getParameter("userId"));

        // Check admin
        if (adminDao.checkIsAdmin(adminId)) {
            // Fetch all the courses created by the adminId
            List<CourseByAdmin> courses = courseByAdminDao.findAllCoursesByAdmin(adminId);

            if (courses.isEmpty()) {
                responseData.put("status", "empty");
            } else {
                String coursesJson = new Gson().toJson(courses);
                responseData.put("status", "success");
                responseData.put("data", coursesJson);
            }
        }
        // Check admin fails
        else {
            responseData.put("status", "error-user");
        }

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
        long adminId = Long.parseLong(request.getParameter("adminId"));
        String courseCode = request.getParameter("courseCode");
        String semester = request.getParameter("semester");

        // Check admin
        if (adminDao.checkIsAdmin(adminId)) {

            // Delete all students enrolled in the course
            boolean statusDeleteAll = studentCourseEnrolledDao.deleteAll(adminId, courseCode, semester);
            // Delete the course
            boolean statusDelete = courseByAdminDao.delete(adminId, courseCode, semester);

            // Delete Success
            if (statusDeleteAll && statusDelete) {
                out.print("success");
            }
            // Delete Fails
            else {
                out.print("error-sql");
            }
        }
        // Check admin fails
        else {
            out.print("error-user");
        }

        // Close output
        out.flush();
        out.close();
    }
}
