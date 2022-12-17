package com.soen387.servlet;

import com.google.gson.Gson;
import com.soen387.inheritance.Person;
import com.soen387.inheritance.PersonTableInheritanceMapper;
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

    private static PersonTableInheritanceMapper mapper;

    public void init() {
        mapper = new PersonTableInheritanceMapper();
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
        if (mapper.checkUserRegistered(userId)) {
            responseData.put("status", "error-registered");
            String json = new Gson().toJson(responseData);
            response.getWriter().write(json);
        }
        // If not registered yet -> register user info
        else {
            /* TODO: insert address */

            String userPassword = generatePassword();

            // Insert user info to Person database
            Person person = new Person(userId, userPassword, userFirstName, userLastName, userAddress, userEmail, userPhone, userDOB);
            // Get user data
            boolean status = mapper.insert(person, userType);

            if (status) {
                responseData.put("status", "success");
                responseData.put("password", userPassword);
                String json = new Gson().toJson(responseData);
                response.getWriter().write(json);
            }
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