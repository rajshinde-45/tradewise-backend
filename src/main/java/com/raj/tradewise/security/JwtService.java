package com.raj.tradewise.security;

import com.raj.tradewise.entity.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //Builder
    public String generateToken(AppUser user){
        return Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expiration)).signWith(getSigningKey()).compact();
    }

    private Claims extracAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token){
        return extracAllClaims(token)
                .getExpiration()
                .before(new Date());
    }
    public String extractEmail(String token){
        return extracAllClaims(token).getSubject();
    }

    public boolean validateToken(String token,AppUser user){
        String email = extractEmail(token);
        return email.equals(user.getEmail()) && !isTokenExpired(token);
    }



}
