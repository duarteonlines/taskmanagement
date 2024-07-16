package com.pedro.taskmanagement.Task.dtos;

import com.pedro.taskmanagement.Task.model.Task;
import com.pedro.taskmanagement.User.dtos.UserListDTO;

import java.util.UUID;

public class TaskListDTO {

    private UUID id;
    private String title;
    private String description;
    private UserListDTO user;

    public TaskListDTO() {

    }

    public TaskListDTO(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.user  = task.getUser() != null ? new UserListDTO(task.getUser()) : null;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public UserListDTO getUser() {
        return user;
    }

    public void setUser(UserListDTO user) {
        this.user = user;
    }
}
