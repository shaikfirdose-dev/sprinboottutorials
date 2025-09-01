package com.firdose.springbootwebweek2.springbootwebweek2.service;

import com.firdose.springbootwebweek2.springbootwebweek2.dto.SignUpDto;
import com.firdose.springbootwebweek2.springbootwebweek2.dto.UserDto;
import com.firdose.springbootwebweek2.springbootwebweek2.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user){
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("roles", Set.of("USER", "ADMIN"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()*1000*60))
                .signWith(getSecretKey())
                .compact();
    }

    public Long getUserIdFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.valueOf(claims.getSubject());
    }

}
