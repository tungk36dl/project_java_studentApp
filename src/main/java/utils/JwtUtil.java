package utils;

import beans.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    private static final String SECRET_KEY = "your-very-secret-key-with-at-least-256-bits-length";
    private static final long JWT_EXPIRATION_MS = 15 * 60 * 1000; // 15 phút
    private static final long REFRESH_EXPIRATION_MS = 7 * 24 * 60 * 60 * 1000; // 7 ngày

    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public static String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public static Date getRefreshTokenExpiry() {
        return new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_MS);
    }

    public static Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public static String getUserIdFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }
}
