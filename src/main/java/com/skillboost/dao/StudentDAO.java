// src/main/java/com/skillboost.dao/StudentDAO.java (UPDATED with Skill CRUD)

package com.skillboost.dao;

import com.skillboost.model.Student;
import com.skillboost.model.SkillProgress; // New import
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp; // New import for date/time conversion
import java.time.LocalDateTime; // New import for Java 8 date/time
import java.util.ArrayList; // New import
import java.util.List;      // New import

public class StudentDAO {
    
    // --- Authentication Method ---
    public Student validate(String email, String password) {
        String SELECT_STUDENT_SQL = "SELECT id, name, email, password FROM student WHERE email = ? AND password = ?";
        Student student = null;

        try (Connection connection = DBConnect.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_SQL)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password); 

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String dbEmail = rs.getString("email");
                String dbPassword = rs.getString("password");
                student = new Student(id, name, dbEmail, dbPassword);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }
    
    // --- Skill CRUD Methods ---
    
    // C: Create (Insert) / U: Update (Upsert)
    public boolean saveOrUpdateSkill(SkillProgress skill) {
        // Requires 'skill_progress' table to have a UNIQUE KEY on (student_id, skill_name)
        String SQL = "INSERT INTO skill_progress (student_id, skill_name, current_level) VALUES (?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE current_level = VALUES(current_level), last_updated = NOW()";

        try (Connection connection = DBConnect.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)) {
            
            ps.setInt(1, skill.getStudentId());
            ps.setString(2, skill.getSkillName());
            ps.setDouble(3, skill.getCurrentLevel());
            
            int rowsAffected = ps.executeUpdate();
            // returns true if a row was inserted (1) or updated (2)
            return rowsAffected > 0; 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // R: Read (Select all skills for a student)
    public List<SkillProgress> getSkillsByStudentId(int studentId) {
        List<SkillProgress> skillList = new ArrayList<>();
        String SQL = "SELECT progress_id, student_id, skill_name, current_level, last_updated FROM skill_progress WHERE student_id = ?";
        
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)) {
            
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int progressId = rs.getInt("progress_id");
                String skillName = rs.getString("skill_name");
                double currentLevel = rs.getDouble("current_level");
                Timestamp timestamp = rs.getTimestamp("last_updated");
                
                // Convert SQL Timestamp to Java 8 LocalDateTime
                LocalDateTime lastUpdated = (timestamp != null) ? timestamp.toLocalDateTime() : null;
                
                skillList.add(new SkillProgress(progressId, studentId, skillName, currentLevel, lastUpdated));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skillList;
    }
}