

package com.skillboost.dao;

import com.skillboost.model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDAO {
    
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
}