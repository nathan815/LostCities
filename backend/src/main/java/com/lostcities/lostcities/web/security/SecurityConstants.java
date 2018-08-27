package com.lostcities.lostcities.web.security;

public class SecurityConstants {
    private static final long TEN_DAYS = 864_000_000;

    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = TEN_DAYS;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTH_HEADER_NAME = "Authorization";
}
