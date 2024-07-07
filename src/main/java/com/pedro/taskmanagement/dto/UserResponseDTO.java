package com.pedro.taskmanagement.dto;

import java.io.Serializable;

import com.pedro.taskmanagement.domain.User;

public class UserResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String username;
    private String email;

    public UserResponseDTO() {
    }

    public UserResponseDTO(User obj) {
        id = obj.getId();
        username = obj.getUsername();
        email = obj.getEmail();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
