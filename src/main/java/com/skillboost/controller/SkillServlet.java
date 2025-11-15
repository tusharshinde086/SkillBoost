// src/main/java/com/skillboost.controller/SkillServlet.java

package com.skillboost.controller;

import com.skillboost.dao.StudentDAO;
import com.skillboost.model.SkillProgress;
import com.skillboost.model.Student;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/skill")
public class SkillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;

    public void init() {
        studentDAO = new StudentDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Student student = (session != null) ? (Student) session.getAttribute("currentStudent") : null;

        if (student == null) {
            response.sendRedirect("login"); // Redirect if not logged in
            return;
        }

        // 1. Get parameters from the form
        String skillName = request.getParameter("skillName");
        // Parse level, ensure it's a number
        double level;
        try {
            level = Double.parseDouble(request.getParameter("currentLevel"));
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid level entered.");
            response.sendRedirect("dashboard");
            return;
        }

        // 2. Create the SkillProgress object
        SkillProgress skill = new SkillProgress(student.getId(), skillName, level);

        // 3. Save/Update via DAO
        if (studentDAO.saveOrUpdateSkill(skill)) {
            request.getSession().setAttribute("message", "Skill '" + skillName + "' updated successfully!");
        } else {
            request.getSession().setAttribute("message", "Error updating skill.");
        }

        // 4. Redirect back to dashboard to see updated list
        response.sendRedirect("dashboard");
    }
}