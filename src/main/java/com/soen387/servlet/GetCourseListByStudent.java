package com.soen387.servlet;

import com.google.gson.Gson;
import com.soen387.beans.CourseByAdmin;
import com.soen387.beans.Person;
import com.soen387.dao.*;
import com.soen387.daoImpl.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "getCourseListByStudent", value = "/getCourseListByStudent")
public class GetCourseListByStudent extends HttpServlet {

    private static PersonDao personDao;
    private static AdminDao adminDao;
    private static StudentDao studentDao;
    private static CourseByAdminDao courseByAdminDao;
    private static StudentCourseEnrolledDao studentCourseEnrolledDao;
    private static PrintWriter out;

    public void init() {
        adminDao = new AdminDaoImpl();
        personDao = new PersonDaoImpl();
        studentDao = new StudentDaoImpl();
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
        Map<String, String> lst = new LinkedHashMap<>();
        List<String> coursesLst = new ArrayList<>();

        // Extract request parameters
        long adminId = Long.parseLong(request.getParameter("userId"));
        long studentId = Long.parseLong(request.getParameter("studentId"));

        // Check admin
        if (adminDao.checkIsAdmin(adminId)) {
            // If given student ID exists
            if (!studentDao.checkIsStudent(studentId)) {
                responseData.put("status", "empty");
            }
            // If no student ID found
            else {
                // Get student info
                Person studentInfo = personDao.findUserInfo(studentId);

                // Fetch all the courses enrolled by a student
                List<CourseByAdmin> courses = studentCourseEnrolledDao.findAllCoursesEnrolledByStudent(studentId);

                // If student takes no courses
                if (courses.isEmpty()) {
                    responseData.put("courses", "empty");
                }
                // If student takes courses
                else {
                    for (CourseByAdmin course: courses) {
                        // Get professor info for each course
                        long profId = course.getAdminID();
                        Person prof = personDao.findUserInfo(profId);

                        String courseJson = new Gson().toJson(course);
                        String profJson = new Gson().toJson(prof);
                        lst.put("course", courseJson);
                        lst.put("prof", profJson);

                        String lstJson = new Gson().toJson(lst);
                        coursesLst.add(lstJson);
                    }
                    String coursesJson = new Gson().toJson(coursesLst);
                    responseData.put("courses", coursesJson);
                }
                // Output
                String studentJson = new Gson().toJson(studentInfo);
                responseData.put("status", "success");
                responseData.put("student", studentJson);
            }

        } else {
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
    }

}
