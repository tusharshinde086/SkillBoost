

package com.skillboost.controller;

import com.skillboost.dao.StudentDAO;
import com.skillboost.model.Student;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;

    public void init() {
        studentDAO = new StudentDAO();
    }

    // it display 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if (name == null || name.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("message", "All fields are required.");
            doGet(request, response); 
            return;
        }

        Student newStudent = new Student(0, name, email, password); 

        if (studentDAO.registerStudent(newStudent)) {
           
            request.getSession().setAttribute("message", "Registration successful! Please log in.");
            response.sendRedirect("login");
        } else {
          
            request.setAttribute("message", "Registration failed. Email may already be in use.");
            doGet(request, response); 
        }
    }
}