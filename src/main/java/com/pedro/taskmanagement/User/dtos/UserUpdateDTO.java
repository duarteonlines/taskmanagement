package com.pedro.taskmanagement.User.dtos;

import com.pedro.taskmanagement.User.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserUpdateDTO  {


    @NotBlank(message = " is null")
    private String name;
    @NotBlank(message = " is null")
    private String username;
    @NotBlank(message = " is null")
    @Email(message = " invalid")
    private String email;
    @NotBlank(message = " is null")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = " must contain at least one uppercase letter, one lowercase letter, one number and one special character")
    private String password;;
    public UserUpdateDTO() {
    }

    public UserUpdateDTO(User obj) {
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
