package me.liskoh.counter.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.HashMap;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String key;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(UserDetails details) {
        return generateToken(details, new HashMap<>());
    }

    public String generateToken(UserDetails details, HashMap<String, Object> claims) {
        return Jwts.builder()
                .subject(details.getUsername())
                .signWith(getKey())
                .claims(claims)
                .compact();
    }

    public boolean isExpired(String token, long now) {
        return getExpiration(token) < now;
    }

    private long getExpiration(String token) {
        Claims claims = getClaims(token);
        if (claims == null || claims.getExpiration() == null) {
            return Long.MAX_VALUE;
        }

        return claims.getExpiration().getTime();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if (claims == null || claims.getSubject() == null) {
            return null;
        }

        return claims.getSubject();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(this.key.getBytes());
    }
}
