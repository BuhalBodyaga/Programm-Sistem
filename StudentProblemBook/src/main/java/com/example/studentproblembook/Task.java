package com.example.studentproblembook;

import com.example.studentproblembook.Priority;
import com.example.studentproblembook.Status;

import java.util.Date;

public class Task {
    private String description;
    private Date deadline;
    private Priority priority;
    private Status status;

    public Task(String description, Date deadline, Priority priority) {
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.status = Status.IN_PROGRESS;
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", deadline=" + deadline +
                ", priority=" + priority +
                ", status=" + status +
                '}';
    }

    // Геттеры и сеттеры для description, deadline, priority и status

    public void set_description(String description) {
        this.description = description;
    }

    public String get_description() {
        return this.description;
    }

    public void set_deadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date get_deadline() {
        return this.deadline;
    }

    public void set_priority(Priority priority) {
        this.priority = priority;
    }

    public Priority get_priority() {
        return this.priority;
    }

    public void set_status(Status status) {
        this.status = status;
    }

    public Status get_status() {
        return this.status;
    }
}
