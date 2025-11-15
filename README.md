# SkillBoost   Project .

# SkillBoost Tracker: Java/Servlet/JSP Skill tracking  System

SkillBoost Tracker is a robust, full-stack web application designed to help individual students manage and track their progress across multiple skills  and enhance it by time to time (e.g., programming languages, frameworks, methodologies). Built on the foundational Java EE stack codes  in (Servlets/JSP) 

## Key Features Implemented:

1.  **Secure Authentication (Login)
2.  **Full CRUD Operations for Skills:**
    * **Create (Add):** Students can enter new skills and set initial proficiency levels (1.0 - 10.0).
    * **Read (Dashboard):** Displays a list of all tracked skills with visual progress bars.
    * **Update (Edit):** Allows users to modify the proficiency level of an existing skill.
    * **Delete:** Removes a tracked skill entry from the database.   
3.  **Data Persistence (`Remember It`):** Utilizes the `user_preferences` table to store and retrieve user-specific dashboard preferences (e.g., preferred skill sorting order), providing a personalized experience across sessions.
4.  **Database Integration:** Uses standard JDBC code and  MySQL database.
5.  **Technology Stack:** Java (JDK 24), Apache Tomcat 8.5 (Servlet 3.1), JSP, 

      --   databasse    mysql  code   -----
        

USE skillboost_db;
DROP TABLE IF EXISTS skill_progress;
DROP TABLE IF EXISTS student;

CREATE TABLE student (
    id INT(11) NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE skill_progress (
    progress_id INT(11) NOT NULL AUTO_INCREMENT,
    student_id INT(11) NOT NULL,
    skill_name VARCHAR(100) NOT NULL,
    current_level DECIMAL(4,2) NOT NULL DEFAULT 0.0,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (progress_id),
    FOREIGN KEY (student_id) REFERENCES student(id),
    UNIQUE KEY uk_student_skill (student_id, skill_name)
);

INSERT INTO student (name, email, password) VALUES
('Test User A', 'test@example.com', 'password'),
('tushar', 'tushar@mail', 'tushar'),
('shruti', 'shruti@gmail.com', 'shruti');
        