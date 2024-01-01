package me.liskoh.counter.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
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
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(details.getUsername())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
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

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if (claims == null || claims.getSubject() == null) {
            return null;
        }

        return claims.getSubject();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}