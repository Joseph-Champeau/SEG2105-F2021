package com.example.deliverable1fixed;

public class ClassType {

    public String name;
    public String description;

    public ClassType() {}

    public ClassType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getClassTypeName() {
        return this.name;
    }

    public String getClassTypeDesc() {
        return this.description;
    }
}
