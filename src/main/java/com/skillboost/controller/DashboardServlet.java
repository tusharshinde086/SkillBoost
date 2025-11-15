

package com.skillboost.controller;

import com.skillboost.dao.StudentDAO;
import com.skillboost.model.Student;
import com.skillboost.model.SkillProgress;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;

    public void init() {
        studentDAO = new StudentDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Student student = (session != null) ? (Student) session.getAttribute("currentStudent") : null;

        if (student != null) {
            // 1. Check for Edit in mm
            String editIdStr = request.getParameter("edit");
            if (editIdStr != null && !editIdStr.isEmpty()) {
                try {
                    int editId = Integer.parseInt(editIdStr);
                  
                    SkillProgress skillToEdit = studentDAO.getSkillById(editId); 
                    
                    
                    if (skillToEdit != null && skillToEdit.getStudentId() == student.getId()) {
                        request.setAttribute("skillToEdit", skillToEdit);
                    }
                } catch (NumberFormatException e) {
                    // Ignore    if errors. ;
                }
            }

            // 2. Load all skills for the current student  in app .
            List<SkillProgress> skills = studentDAO.getSkillsByStudentId(student.getId());
            request.setAttribute("skillList", skills); 
            
            // 3. for messege handlingss
            String message = (String) session.getAttribute("message");
            if (message != null) {
                request.setAttribute("message", message);
                session.removeAttribute("message");
            }
            
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }
}