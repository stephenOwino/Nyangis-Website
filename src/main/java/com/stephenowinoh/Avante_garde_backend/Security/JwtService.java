package com.stephenowinoh.Avante_garde_backend.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

        @Value("${jwt.secret}")
        private String secret;

        @Value("${jwt.expiration}")
        private long validity;

        public String generateToken(UserDetails userDetails) {
                Map<String, String> claims = new HashMap<>();
                return Jwts.builder()
                        .claims(claims)
                        .subject(userDetails.getUsername())
                        .issuedAt(Date.from(Instant.now()))
                        .expiration(Date.from(Instant.now().plusMillis(validity)))
                        .signWith(generateKey())
                        .compact();
        }

        private SecretKey generateKey() {
                byte[] decodedKey = Base64.getDecoder().decode(secret);
                return Keys.hmacShaKeyFor(decodedKey);
        }

        public String extractUsername(String jwt) {
                try {
                        Claims claims = getClaims(jwt);
                        return claims.getSubject();
                } catch (Exception e) {
                        System.out.println("Failed to extract username from JWT: " + e.getMessage()); // Debug log
                        return null;
                }
        }

        private Claims getClaims(String jwt) {
                return Jwts.parser()
                        .verifyWith(generateKey())
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();
        }

        public boolean isTokenValid(String jwt) {
                try {
                        Claims claims = getClaims(jwt);
                        boolean isValid = claims.getExpiration().after(Date.from(Instant.now()));
                        System.out.println("Token validity check: " + isValid); // Debug log
                        return isValid;
                } catch (Exception e) {
                        System.out.println("Token validation failed: " + e.getMessage()); // Debug log
                        return false;
                }
        }
}