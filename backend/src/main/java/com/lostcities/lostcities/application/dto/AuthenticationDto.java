package com.lostcities.lostcities.application.dto;

public class AuthenticationDto {

    private String token;
    private UserDto user;

    public AuthenticationDto(String token, UserDto user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
