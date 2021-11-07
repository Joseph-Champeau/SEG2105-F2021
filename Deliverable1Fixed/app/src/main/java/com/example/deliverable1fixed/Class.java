package com.example.deliverable1fixed;

public class Class {

    public String name;
    public User instructor;
    public ClassType classType;
    public String difficultyLevel;
    public String day;
    public String timeInterval;
    public String capacity;

    public Class() {}

    public Class(String name, User instructor, ClassType classType, String difficultyLevel, String day, String timeInterval, String capacity) {
        this.name = name;
        this.instructor = instructor;
        this.classType = classType;
        this.difficultyLevel = difficultyLevel;
        this.day = day;
        this.timeInterval = timeInterval;
        this.capacity = capacity;
    }
}
