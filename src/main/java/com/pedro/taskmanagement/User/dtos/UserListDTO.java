package com.pedro.taskmanagement.User.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pedro.taskmanagement.Task.model.Task;
import com.pedro.taskmanagement.User.model.User;

import java.util.UUID;

public class UserListDTO {

    private UUID id;
    private String username;
    private String email;
    @JsonIgnore
    private Task task;

    public UserListDTO() {
    }

    public UserListDTO(User obj) {
        id = obj.getId();
        username = obj.getUsername();
        email = obj.getEmail();
        task = obj.getTask();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Task getTask() { return task; }

    public void setTask(Task task) { this.task = task; }
}
