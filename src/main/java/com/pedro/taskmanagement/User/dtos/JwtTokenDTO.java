package com.pedro.taskmanagement.User.dtos;

public class JwtTokenDTO {
    String token;

    public JwtTokenDTO(String token) {
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }
}
