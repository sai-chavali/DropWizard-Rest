package com.java.todo.domain;

import org.bson.types.ObjectId;

import java.util.List;

public class Task {
    private String name;
    private String description;
    private List<SubTask> subtasks;

    public Task() {}

    public Task(String name, String description, List<SubTask> subtasks) {
        this.name = name;
        this.description = description;
        this.subtasks = subtasks;
    }

    public List<SubTask> getSubtasks() {
        return subtasks;
    }

    public void setSubasks(List<SubTask> subtasks) {
        this.subtasks = subtasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
