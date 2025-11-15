
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

    // Handles Add/Update
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Student student = (session != null) ? (Student) session.getAttribute("currentStudent") : null;

        if (student == null) {
            response.sendRedirect("login");
            return;
        }

        // 1. Get parameters
        String skillName = request.getParameter("skillName"); // Input box
        String levelStr = request.getParameter("currentLevel");
        String progressIdStr = request.getParameter("progressId"); // Hidden field for updates

        // 2. Validation
        if (skillName == null || skillName.trim().isEmpty() || levelStr == null) {
             session.setAttribute("message", "Skill name and level are required.");
             response.sendRedirect("dashboard");
             return;
        }
        
        double level;
        try {
            level = Double.parseDouble(levelStr);
        } catch (NumberFormatException e) {
            session.setAttribute("message", "Invalid level entered.");
            response.sendRedirect("dashboard");
            return;
        }

        // 3. Create the SkillProgress object in app
        SkillProgress skill = new SkillProgress(student.getId(), skillName, level);
        
   

        // 4. Save/Update via DAO
        if (studentDAO.saveOrUpdateSkill(skill)) {
            session.setAttribute("message", "Skill '" + skillName + "' updated successfully!");
        } else {
            session.setAttribute("message", "Error updating skill.");
        }

        response.sendRedirect("dashboard");
    }

    // Delete
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Student student = (session != null) ? (Student) session.getAttribute("currentStudent") : null;

        String deleteIdStr = request.getParameter("delete");

        if (student == null || deleteIdStr == null || deleteIdStr.isEmpty()) {
            response.sendRedirect("login"); 
            return;
        }

        try {
            int deleteId = Integer.parseInt(deleteIdStr);
            
            
            
            if (studentDAO.deleteSkill(deleteId)) {
                session.setAttribute("message", "Skill deleted successfully!");
            } else {
                session.setAttribute("message", "Error deleting skill.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("message", "Invalid delete request.");
        }
        
        response.sendRedirect("dashboard");
    }
}