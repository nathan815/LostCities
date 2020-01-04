package com.lostcities.lostcities.domain.user;

public class User {
    private long id;
    private boolean isAdmin;
    private String username;

    public User(long id, String username) {
        this.id = id;
        this.isAdmin = false;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getUsername() {
        return username;
    }
}
