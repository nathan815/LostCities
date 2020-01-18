package com.lostcities.lostcities.web.security;

import com.lostcities.lostcities.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collections;
import java.util.Date;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtTokenHelper {

    private static final String USER_CLAIM_KEY = "user";

    public static Authentication parseAuthenticationToken(String token) {
        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            if(claims.get(USER_CLAIM_KEY) != null) {
                return new UsernamePasswordAuthenticationToken(claims.get(USER_CLAIM_KEY), null,
                        Collections.singleton(new SimpleGrantedAuthority("USER")));
            }
        }
        return null;
    }

    public static String generateToken(Authentication auth) {
        User user = ((AuthUser) auth.getPrincipal()).toDomainUser();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim(USER_CLAIM_KEY, user)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    public static String parseTokenFromHeader(String header) {
        return header.replace(SecurityConstants.TOKEN_PREFIX, "");
    }

}
