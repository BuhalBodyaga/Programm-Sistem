package com.example.studentproblembook;

import com.example.studentproblembook.User;

import java.util.ArrayList;
import java.util.List;

public class TaskPlanner {
    private List<Task> tasks;
    private User user;

    public TaskPlanner(User user) {
        this.tasks = new ArrayList<>();
        this.user = user;
    }

    public void add_task(Task task) {
        tasks.add(task);
    }

    public void remove_task(Task task) {
        tasks.remove(task);
    }

    public void mark_task_as_complete(Task task) {
        task.set_status(Status.COMPLETED);
    }

    public List<Task> get_tasks() {
        return tasks;
    }
}
