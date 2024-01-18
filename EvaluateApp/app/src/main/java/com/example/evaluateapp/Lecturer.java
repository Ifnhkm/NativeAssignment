package com.example.evaluateapp;

// Lecturer.java
public class Lecturer {
    private int id; // New field for ID
    private String name;
    private String subject;
    private int imageResourceId; // Change from String to int
    private boolean evaluated;

    public Lecturer(int id, String name, String subject, int imageResourceId) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.imageResourceId = imageResourceId;
        this.evaluated = false;
    }

    public boolean isEvaluated() {
        return evaluated;
    }

    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
