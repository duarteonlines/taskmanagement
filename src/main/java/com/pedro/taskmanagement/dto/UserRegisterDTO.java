package com.pedro.taskmanagement.dto;

import com.pedro.taskmanagement.domain.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serial;
import java.io.Serializable;

public class UserRegisterDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Name is null")
    private String name;
    @NotBlank(message = "Username is null")
    private String username;
    @NotBlank(message = "Email is null")
    @Email(message = "Email invalid")
    private String email;
    @NotBlank(message = "Password is null")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one number and one special character")
    private String password;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(User obj) {
        name = obj.getName();
        username = obj.getUsername();
        email = obj.getEmail();
        password = obj.getPassword();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
