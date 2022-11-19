package com.soen387.servlet;

import com.soen387.beans.CourseByAdmin;
import com.soen387.dao.AdminDao;
import com.soen387.dao.CourseByAdminDao;
import com.soen387.daoImpl.AdminDaoImpl;
import com.soen387.daoImpl.CourseByAdminDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@WebServlet(name = "createCourse", value = "/createCourse")
public class CreateCourse extends HttpServlet {
    private static AdminDao adminDao;
    private static CourseByAdminDao courseByAdminDao;

    public void init() {
        adminDao = new AdminDaoImpl();
        courseByAdminDao = new CourseByAdminDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Prepare output
        PrintWriter out = response.getWriter();

        // Extract request parameters
        long adminId = Long.parseLong(request.getParameter("adminId"));
        String courseCode = request.getParameter("courseCode");
        String courseTitle = request.getParameter("courseTitle");
        String semester = request.getParameter("semester");
        String daysOfWeek = Objects.equals(request.getParameter("daysOfWeek"), "[]")
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
        if (adminDao.checkIsAdmin(adminId)) {
            // Create instance
            CourseByAdmin course = new CourseByAdmin(adminId, courseCode, courseTitle, semester, daysOfWeek, startTime, endTime, room, startDate, endDate);

            // If class date time overlaps with other classes created by admin
            if (courseByAdminDao.checkClassTimeOverlap(course)){
                out.print("error-overlap");
            }
            // Insert success
            else if (courseByAdminDao.insert(course)) {
                out.print("success");
            }
            // Insert fails due to sql integrity error
            else {
                out.print("error-registered");
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
