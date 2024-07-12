package com.pedro.taskmanagement.dto;

import com.pedro.taskmanagement.domain.task.Task;
import com.pedro.taskmanagement.domain.user.User;

public class TaskDTO {

    private String title;
    private String description;
    private User user;

    public TaskDTO(Task task) {
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.user = task.getUser();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
