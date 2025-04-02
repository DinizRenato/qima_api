package com.qima.tech.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String SECRET_KEY = "H3cG95YRDdrk1YSt8Mx7Q04ro3GqGWaUsOAki4Ih6";

    public String generateToken(String username) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.builder()
                .subject(username)
                .signWith(key)
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .compact();
    }

}
