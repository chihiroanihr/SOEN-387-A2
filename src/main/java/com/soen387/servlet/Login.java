package com.soen387.servlet;

import com.soen387.dao.AdminDao;
import com.soen387.dao.PersonDao;
import com.soen387.dao.StudentDao;
import com.soen387.daoImpl.AdminDaoImpl;
import com.soen387.daoImpl.PersonDaoImpl;
import com.soen387.beans.Person;
import com.soen387.daoImpl.StudentDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
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

        // Extract request parameters
        long userId = Long.parseLong(request.getParameter("userID"));
        String password = request.getParameter("userPassword");
        String userType = request.getParameter("userType");

        // Get user data
        Person person = personDao.authenticateUser(userId, password);

        // If this userId exists and is a student
        if (person != null && Objects.equals(userType, "student") && studentDao.checkIsStudent(userId)) {
            // create session
            createSession(request, person, userType);
            out.print("success");
        }
        // If this userId exists and is an admin
        else if (person != null && Objects.equals(userType, "admin") && adminDao.checkIsAdmin(userId)) {
            // create session
            createSession(request, person, userType);
            out.print("success");
        }
        // If userId does not exist
        else {
            out.print("error");
        }

        // Close output
        out.flush();
        out.close();
    }

    private void createSession(HttpServletRequest request, Person person, String userType) {
        // Create new session if it does not exist
        HttpSession session = request.getSession(true);
        session.setAttribute("login", "logged");
        session.setAttribute("userId", person.getPersonId());
        session.setAttribute("userName", person.getFirstName() + ' ' + person.getLastName());
        session.setAttribute("userEmail", person.getEmail());
        session.setAttribute("userPhoneNum", person.getPhoneNum());
        session.setAttribute("userType", userType);
    }
}