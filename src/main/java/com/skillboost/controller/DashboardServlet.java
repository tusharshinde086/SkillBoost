// src/main/java/com/skillboost.controller/DashboardServlet.java

package com.skillboost.controller;

import com.skillboost.dao.StudentDAO; // Import DAO
import com.skillboost.model.Student;  // Import Student model
import com.skillboost.model.SkillProgress; // Import SkillProgress model
import java.io.IOException;
import java.util.List;                 // Import List
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO; // Declare DAO instance

    // Initialize DAO when the servlet is created
    public void init() {
        studentDAO = new StudentDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Student student = (session != null) ? (Student) session.getAttribute("currentStudent") : null;

        if (student != null) {
            // New Logic: Load skills for the current student
            // This line requires the getSkillsByStudentId method in StudentDAO
            List<SkillProgress> skills = studentDAO.getSkillsByStudentId(student.getId());
            request.setAttribute("skillList", skills); // Attach list to request
            
            // Handle flash message from redirect (e.g., after updating a skill)
            String message = (String) session.getAttribute("message");
            if (message != null) {
                request.setAttribute("message", message);
                session.removeAttribute("message"); // Remove message so it doesn't reappear
            }
            
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
        } else {
            // Not logged in, redirect to login page
            response.sendRedirect("login");
        }
    }
}