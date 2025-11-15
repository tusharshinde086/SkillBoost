

package com.skillboost.dao;

import com.skillboost.model.Student;
import com.skillboost.model.SkillProgress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    
    //  (R) ---
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
    
    //  CRUD Methods ---
    
    // C: Create 
    public boolean saveOrUpdateSkill(SkillProgress skill) {
        
        String SQL = "INSERT INTO skill_progress (student_id, skill_name, current_level) VALUES (?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE current_level = VALUES(current_level), last_updated = NOW()";

        try (Connection connection = DBConnect.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)) {
            
            ps.setInt(1, skill.getStudentId());
            ps.setString(2, skill.getSkillName());
            ps.setDouble(3, skill.getCurrentLevel());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // R: Read ALL skills 
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
                LocalDateTime lastUpdated = (timestamp != null) ? timestamp.toLocalDateTime() : null;
                
                skillList.add(new SkillProgress(progressId, studentId, skillName, currentLevel, lastUpdated));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skillList;
    }

    // R: Read ONE skill by its ID 
    public SkillProgress getSkillById(int progressId) {
        String SQL = "SELECT progress_id, student_id, skill_name, current_level, last_updated FROM skill_progress WHERE progress_id = ?";
        SkillProgress skill = null;
        
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)) {
            
            ps.setInt(1, progressId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int studentId = rs.getInt("student_id");
                String skillName = rs.getString("skill_name");
                double currentLevel = rs.getDouble("current_level");
                Timestamp timestamp = rs.getTimestamp("last_updated");
                LocalDateTime lastUpdated = (timestamp != null) ? timestamp.toLocalDateTime() : null;
                
                skill = new SkillProgress(progressId, studentId, skillName, currentLevel, lastUpdated);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skill;
    }
 

 // C: Create a new student in dbb
 public boolean registerStudent(Student student) {
     
     if (isEmailRegistered(student.getEmail())) {
         return false; 
     }

     String INSERT_STUDENT_SQL = "INSERT INTO student (name, email, password) VALUES (?, ?, ?)";

     try (Connection connection = DBConnect.getConnection();
          PreparedStatement ps = connection.prepareStatement(INSERT_STUDENT_SQL)) {
         
         ps.setString(1, student.getName());
         ps.setString(2, student.getEmail());
         ps.setString(3, student.getPassword());
         
         return ps.executeUpdate() > 0;
     } catch (Exception e) {
         e.printStackTrace();
         return false;
     }
 }

 // R: Helper method to check if an email exists
 public boolean isEmailRegistered(String email) {
     String SELECT_EMAIL_SQL = "SELECT id FROM student WHERE email = ?";
     boolean exists = false;

     try (Connection connection = DBConnect.getConnection();
          PreparedStatement ps = connection.prepareStatement(SELECT_EMAIL_SQL)) {
         
         ps.setString(1, email);
         ResultSet rs = ps.executeQuery();
         
         exists = rs.next(); 
     } catch (Exception e) {
         e.printStackTrace();
     }
     return exists;
 }
    // D: Delete 
    public boolean deleteSkill(int progressId) {
        String SQL = "DELETE FROM skill_progress WHERE progress_id = ?";

        try (Connection connection = DBConnect.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)) {
            
            ps.setInt(1, progressId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}