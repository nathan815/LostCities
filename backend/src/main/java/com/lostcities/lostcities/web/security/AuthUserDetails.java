package com.lostcities.lostcities.web.security;

import com.lostcities.lostcities.domain.user.User;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;

public class AuthUserDetails extends org.springframework.security.core.userdetails.User {

    private long id;

    public AuthUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
                           boolean credentialsNonExpired, boolean accountNonLocked,
                           Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public AuthUserDetails(long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public AuthUserDetails(long id, String username, String password) {
        this(id, username, password, Collections.emptyList());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User toDomainUser() {
        return new User(getId(), getUsername());
    }

}
