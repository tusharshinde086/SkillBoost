// src/main/java/com/skillboost.dao/DBConnect.java (REWRITTEN TO HARDCODE)

package com.skillboost.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
    
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/skillboost_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";  
    private static final String PASS = "root"; 

    static {
        try {
            Class.forName(JDBC_DRIVER); 
            System.out.println("MySQL Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }
    
    public static Connection getConnection() {
        try {
            // Use the hardcoded variables
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }
}