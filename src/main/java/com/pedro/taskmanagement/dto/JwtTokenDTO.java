package com.pedro.taskmanagement.dto;

public class JwtTokenDTO {
    String token;

    public JwtTokenDTO(String token) {
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }
}
