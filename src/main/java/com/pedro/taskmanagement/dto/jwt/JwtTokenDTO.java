package com.pedro.taskmanagement.dto.jwt;

public class JwtTokenDTO {
    String token;

    public JwtTokenDTO(String token) {
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }
}
