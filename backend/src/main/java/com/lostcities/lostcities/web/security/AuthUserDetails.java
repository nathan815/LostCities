package com.lostcities.lostcities.web.security;


import com.lostcities.lostcities.domain.user.User;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUserDetails implements UserDetails {

    long id;
    String username;
    String hashedPassword;
    Collection<? extends GrantedAuthority> authorities;

    public AuthUserDetails(long id, String username, String hashedPassword, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.authorities = authorities;
    }

    public AuthUserDetails(long id, String username, String hashedPassword) {
        this(id, username, hashedPassword, Collections.emptyList());
    }


    public User toUser() {
        return new User(id, false, username);
    }

    public long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
