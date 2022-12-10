package com.marcelus.securinguserviceexercise.security.service.utils;

import com.marcelus.securinguserviceexercise.security.service.SecurityController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class JwtUtil {

    private static final String SECRET_KEY ="foobarfizzbuzz";

    public String extractUserName(String token){
        return retrieveClaim(token, Claims::getSubject);
    }

    public <T> T retrieveClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *10))
                .setIssuer("Security-Service")
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())) && ! retrieveClaim(token, Claims::getExpiration).before(new Date());
    }

    public Boolean validateToken(String token){
        return Objects.equals(retrieveClaim(token, Claims::getIssuer), SecurityController.SERVICE_NAME)
                && ! retrieveClaim(token, Claims::getExpiration).before(new Date());
    }
}

