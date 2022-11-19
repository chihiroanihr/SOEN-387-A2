package com.soen387.servlet;

import com.google.gson.Gson;
import com.soen387.beans.CourseByAdmin;
import com.soen387.beans.Person;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "getStudentListByCourse", value = "/getStudentListByCourse")
public class GetStudentListByCourse extends HttpServlet {

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
        List<String> coursesLst = new ArrayList<>();
        Map<String, String> lst = new LinkedHashMap<>();

        // Extract request parameters
        long adminId = Long.parseLong(request.getParameter("userId"));

        // Check admin
        if (adminDao.checkIsAdmin(adminId)) {
            // Fetch all the courses created by the adminId
            List<CourseByAdmin> courses = courseByAdminDao.findAllCoursesByAdmin(adminId);

            if (courses.isEmpty()) {
                responseData.put("courses", "empty");
            } else {
                for (CourseByAdmin course: courses) {
                    String courseJson = new Gson().toJson(course);
                    lst.put("course", courseJson);

                    String courseCode = course.getCourseCode();
                    String semester = course.getSemester();

                    // Fetch all students info who are enrolled in this course
                    List<Person> students = studentCourseEnrolledDao.findAllStudentInfo(adminId, courseCode, semester);

                    if (students.isEmpty()) {
                        lst.put("students", "empty");
                    } else {
                        String studentsJson = new Gson().toJson(students);
                        lst.put("students", studentsJson);
                    }
                    String lstJson = new Gson().toJson(lst);
                    coursesLst.add(lstJson);
                }
                String coursesJson = new Gson().toJson(coursesLst);
                responseData.put("courses", coursesJson);
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
