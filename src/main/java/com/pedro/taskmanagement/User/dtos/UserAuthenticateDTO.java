package com.pedro.taskmanagement.User.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserAuthenticateDTO {
    @NotBlank(message = "Email is null")
    @Email(message = "Email invalid")
    String email;
    @NotBlank(message = "Password is null")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one number and one special character")
    String password;

    public String getEmail(){
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
