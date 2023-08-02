package ru.isshepelev.jwt.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.isshepelev.jwt.entity.Role;

import java.util.*;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration-ms}")
    private long expirationTimeMs;

    public String generateToken(String username, Role roles) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + expirationTimeMs);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(expirationTime)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token).getBody();
    }
}
