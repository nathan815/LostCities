package com.lostcities.lostcities.domain.user;

public class User {
    private long id;
    private boolean isAdmin;
    private String username;

    public User(long id, boolean isAdmin, String username) {
        this.id = id;
        this.isAdmin = isAdmin;
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
