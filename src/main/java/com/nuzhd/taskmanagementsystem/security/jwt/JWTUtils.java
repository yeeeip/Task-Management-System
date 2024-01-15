package com.nuzhd.taskmanagementsystem.security.jwt;

import com.nuzhd.taskmanagementsystem.model.MyUser;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.util.Date;

public class JWTUtils {

    private final String MY_JWT_SECRET = "my-jwt-secret";

    public String generateJWT(MyUser user) {
        String username = user.getUsername();
        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 10);
        String token = Jwts.builder()
                .setSubject(username)
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, MY_JWT_SECRET)
                .compact();

        return token;
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(MY_JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) throws MalformedJwtException {
        try {
            Jwts.parser()
                    .setSigningKey(MY_JWT_SECRET)
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT has expired");
        } catch (MalformedJwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("Invalid JWT");
        }
        return true;
    }
}
