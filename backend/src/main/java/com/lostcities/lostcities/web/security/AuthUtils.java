package com.lostcities.lostcities.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lostcities.lostcities.domain.user.User;
import java.util.Optional;
import org.springframework.security.core.Authentication;

public class AuthUtils {
    public static Optional<User> getPrincipalFromAuthentication(Authentication auth) {
        ObjectMapper mapper = new ObjectMapper();
        return Optional.ofNullable(mapper.convertValue(auth.getPrincipal(), User.class));
    }
}
