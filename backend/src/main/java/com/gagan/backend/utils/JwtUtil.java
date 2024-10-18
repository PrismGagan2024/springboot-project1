package com.gagan.backend.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Secret key

    public static String generateToken(String email, Map<String, Object> payload) {
        long expirationTimeMillis = 1000 * 60 * 60;
        return Jwts.builder()
                .setClaims(payload)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(key)
                .compact();
    }
}
