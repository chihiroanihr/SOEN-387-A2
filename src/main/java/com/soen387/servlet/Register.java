package com.soen387.servlet;

import com.google.gson.Gson;
import com.soen387.beans.Admin;
import com.soen387.beans.Person;
import com.soen387.beans.Student;
import com.soen387.dao.AdminDao;
import com.soen387.dao.PersonDao;
import com.soen387.dao.StudentDao;
import com.soen387.daoImpl.AdminDaoImpl;
import com.soen387.daoImpl.PersonDaoImpl;
import com.soen387.daoImpl.StudentDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.*;

@WebServlet(name = "register", value = "/register")
public class Register extends HttpServlet {
    private static PersonDao personDao;
    private static StudentDao studentDao;
    private static AdminDao adminDao;

    public void init() {
        personDao = new PersonDaoImpl();
        studentDao = new StudentDaoImpl();
        adminDao = new AdminDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Prepare output
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, String> responseData = new LinkedHashMap<>();

        // Extract request parameters
        long userId = Long.parseLong(request.getParameter("userID"));
        String userFirstName = request.getParameter("userFirstName");
        String userLastName = request.getParameter("userLastName");
        Date userDOB = Date.valueOf(request.getParameter("userDOB"));
        String userAddress = request.getParameter("userAddress");
        String userCity = request.getParameter("userCity");
        String userCountry = request.getParameter("userCountry");
        String userPostal = request.getParameter("userPostal");
        long userPhone = Long.parseLong(request.getParameter("userPhone"));
        String userEmail = request.getParameter("userEmail");
        String userType = request.getParameter("userType");

        // Check if user already registered
        if (personDao.checkUserRegistered(userId)) {
            responseData.put("status", "error-registered");
            String json = new Gson().toJson(responseData);
            response.getWriter().write(json);
        }
        // If not registered yet -> register user info
        else {
            boolean personStatus;
            boolean studentStatus = false;
            boolean adminStatus = false;

            /* TODO: insert address */

            String userPassword = generatePassword();

            // Insert user info to Person database
            Person person = new Person(userId, userPassword, userFirstName, userLastName, userDOB, userEmail, userPhone, userAddress);
            personStatus = personDao.insert(person);

            switch (userType) {
                // Insert user ID into Student database if userType == student
                case "student":
                    Student student = new Student(userId);
                    studentStatus = studentDao.insert(student);
                    break;
                // Insert user ID into Admin database if userType == admin
                case "admin":
                    Admin admin = new Admin(userId);
                    adminStatus = adminDao.insert(admin);
                    break;
            }

            // If insert success
            if (personStatus && studentStatus || personStatus && adminStatus) {
                responseData.put("status", "success");
                responseData.put("password", userPassword);
                String json = new Gson().toJson(responseData);
                response.getWriter().write(json);
            }
            // If insert fails
            else {
                responseData.put("status", "error-sql");
                String json = new Gson().toJson(responseData);
                response.getWriter().write(json);
            }
        }

        // Close output
        out.flush();
        out.close();
    }

    private String generatePassword() {
        return new Random().ints(10, 33, 122)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}