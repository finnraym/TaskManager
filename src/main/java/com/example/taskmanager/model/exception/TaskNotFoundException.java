package com.example.taskmanager.model.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {
        super("Task not found!");
    }
}
