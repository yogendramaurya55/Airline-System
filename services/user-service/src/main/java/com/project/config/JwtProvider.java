package com.project.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class JwtProvider {


    private final long jwtExpirationMs;
    private final SecretKey key;

    public JwtProvider(
            @Value("${app.jwt.secret}") String jwtSecret ,
            @Value("${app.jwt.expiration-ms}") long jwtExpirationMs
    ){
        this.key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
        this.jwtExpirationMs = jwtExpirationMs;
    }


    public String generateToken(Authentication auth , Long userId){
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .claim("email" , auth.getName())
                .claim("authorities" , roles)
                .claim("userId" , userId)
                .signWith(key)
                .compact();


    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {

        Set<String> auths = new HashSet<>();

        for(GrantedAuthority authority : authorities){
            auths.add(authority.getAuthority());
        }

        return String.join("," , auths);
    }

}
