package com.example.ai_resume.domain.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/** Builds and validates JWT access tokens. */
@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long expirationMs;

    /* Constructor-based dependency injection for JWT secret and expiration time from application properties. */
    public JwtService(
        @Value("${app.jwt.secret}") String secret,
        @Value("${app.jwt.expiration-ms}") long expirationMs
    ) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    /**
     * Generates a JWT access token containing the user's ID, email, and role as claims. The token is signed with the configured secret key and has an expiration time based on the configured duration.
     * 
     * @param userId - the ID of the authenticated user to include in the token's subject claim
     * @param email - the email of the authenticated user to include as a claim in the token
     * @param role - the role of the authenticated user to include as a claim in the token
     * @return - a signed JWT access token as a String
     */
    public String generateToken(Long userId, String email, String role) {
        Date now = new Date();
        
        return Jwts.builder()
            .subject(String.valueOf(userId))
            .claim("email", email)
            .claim("role", role)
            .issuedAt(now)
            .expiration(new Date(now.getTime() + expirationMs))
            .signWith(signingKey)
            .compact();
    }

    /**
     * Parse + verify the JWT and return its claims, or throw if invalid/expired.
     *
     * @param token - the JWT to parse and verify
     * @return - the claims contained in the JWT
     */
    public Claims parse(String token) {
        return Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    /**
     * Extract the user ID from the JWT.
     * @param token - the JWT from which to extract the user ID
     * @return - the user ID contained in the JWT
     */
    public Long extractUserId(String token) {
        return Long.valueOf(parse(token).getSubject());
    }
}
