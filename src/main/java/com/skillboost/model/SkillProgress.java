
package com.skillboost.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SkillProgress {
    private int progressId;
    private int studentId;
    private String skillName;
    private double currentLevel; 
    private LocalDateTime lastUpdated;

    //skillls
    public SkillProgress() {}
    
    public SkillProgress(int progressId, int studentId, String skillName, double currentLevel, LocalDateTime lastUpdated) {
        this.progressId = progressId;
        this.studentId = studentId;
        this.skillName = skillName;
        this.currentLevel = currentLevel;
        this.lastUpdated = lastUpdated;
    }

    
    public SkillProgress(int studentId, String skillName, double currentLevel) {
        this.studentId = studentId;
        this.skillName = skillName;
        this.currentLevel = currentLevel;
    }

    // --- Getters and Setters ---
    public int getProgressId() { return progressId; }
    public void setProgressId(int progressId) { this.progressId = progressId; }
    
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    
    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
    
    public double getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(double currentLevel) { this.currentLevel = currentLevel; }
    
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    
    public String getFormattedLastUpdated() {
        if (this.lastUpdated == null) {
            return "N/A";
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return this.lastUpdated.format(formatter);
    }
}