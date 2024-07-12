package com.pedro.taskmanagement.dto;

import com.pedro.taskmanagement.domain.task.Task;
import com.pedro.taskmanagement.domain.user.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class UserResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String username;
    private String email;
    private Task task;

    public UserResponseDTO() {
    }

    public UserResponseDTO(User obj) {
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
