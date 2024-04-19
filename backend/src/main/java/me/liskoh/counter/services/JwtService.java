package me.liskoh.counter.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String key;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(UserDetails details) {
        HashMap<String, Object> claims = new HashMap<>();
        return generateToken(details, claims);
    }

    public String generateToken(UserDetails details, HashMap<String, Object> claims) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .subject(details.getUsername())
                .signWith(getKey())
                .claims(claims)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expiration))
                .compact();
    }

    public boolean isExpired(Claims claims, String token, long now) {
        return getExpiration(claims, token) < now;
    }

    private long getExpiration(Claims claims, String token) {
        if (claims == null || claims.getExpiration() == null) {
            return 0;
        }

        return claims.getExpiration().getTime();
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsername(Claims claims, String token) {
        if (claims == null || claims.getSubject() == null) {
            return null;
        }

        return claims.getSubject();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(this.key.getBytes());
    }
}
