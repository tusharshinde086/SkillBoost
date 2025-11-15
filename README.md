# SkillBoost
      databasse    mysql  code   
      
     
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
        