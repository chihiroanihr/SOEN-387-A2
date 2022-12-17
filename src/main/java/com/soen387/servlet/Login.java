package com.soen387.servlet;

import com.soen387.inheritance.PersonTableInheritanceMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import com.soen387.inheritance.Person;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
    /* No longer need Dao object
    private static PersonDao personDao;
    private static StudentDao studentDao;
    private static AdminDao adminDao;
    */
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

        // Extract request parameters from input
        long userId = Long.parseLong(request.getParameter("userID"));
        String password = request.getParameter("userPassword");
        String userType = request.getParameter("userType");

        // Get user data
        var person = mapper.authenticateUser(userId, password);

        // If person exists - Authentication Success
        if (person != null) {
            System.out.println("Got userType: " + userType + " and person is type " + person.getType());

            // If person is a student or person is an admin
            if (person.getType().equals(userType)) {
                System.out.println("User logged in with type : " + person.getType());
                createSession(request, person, userType);
                out.print("success");
            } else {
                System.out.println("Invalid credentials");
                out.print("error");
            }
        }
        // If person does not exist - Authentication Failure
        else {
            System.out.println("User doesn't exist");
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
        session.setAttribute("userId", person.getID());
        session.setAttribute("userName", person.getFirstName() + ' ' + person.getLastName());
        session.setAttribute("userEmail", person.getEmail());
        session.setAttribute("userPhoneNum", person.getPhoneNumber());
        session.setAttribute("userType", userType);
    }
}