package com.coding.backend.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private String secretKey = "rhldwkdglrlsrmfjsanswkdufdmfsjgdmfkrhehodlTsp";
    private final long expiration = 1000L * 60 * 60 * 24; // 24시간
    private Key signingKey;

    @PostConstruct
    public void init() {
        signingKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // JWT 생성 (userId 기반)
    public String createToken(Integer userId) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put("userId", userId);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰에서 userId 꺼내기
    public Integer getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Object userId = claims.get("userId");

        if (userId instanceof Integer) {
            return (Integer) userId;
        }
            return null;
        }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
