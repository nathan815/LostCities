package com.lostcities.lostcities.web.security;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;

public class AuthUser extends org.springframework.security.core.userdetails.User {

    long id;

    public AuthUser(String username, String password, boolean enabled, boolean accountNonExpired,
                       boolean credentialsNonExpired, boolean accountNonLocked,
                       Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public AuthUser(long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public AuthUser(long id, String username, String password) {
        this(id, username, password, Collections.emptyList());
    }

    public long getId() {
        return id;
    }

}
