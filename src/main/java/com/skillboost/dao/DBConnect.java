

package com.skillboost.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DBConnect {
    private static String JDBC_DRIVER;
    private static String DB_URL;
    private static String USER;
    private static String PASS;

    static {
        try {
            Properties prop = new Properties();
            InputStream input = DBConnect.class.getClassLoader().getResourceAsStream("db.properties");
            
            if (input == null) {
                System.err.println("FATAL ERROR: Unable to find db.properties in the classpath.");
            } else {
                prop.load(input);
                JDBC_DRIVER = prop.getProperty("db.driver");
                DB_URL = prop.getProperty("db.url");
                USER = prop.getProperty("db.username");
                PASS = prop.getProperty("db.password");
                
                Class.forName(JDBC_DRIVER); 
                System.out.println("MySQL Driver loaded successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize database configuration or driver.", e);
        }
    }
    
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            System.err.println("Database connection failed for URL: " + DB_URL);
            e.printStackTrace();
        }
        return connection;
    }
}