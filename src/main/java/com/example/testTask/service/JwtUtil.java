package com.example.testTask.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.KeyPair;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtUtil {


  private final String secret;

  public JwtUtil(@Value("${jwt.secret}") String secret) {
    this.secret = secret;
  }

  public String generateToken(Long userId) {
    Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
            SignatureAlgorithm.HS256.getJcaName());
    return Jwts.builder()
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 3600_000))
            .claim("USER_ID", userId)
            .signWith(hmacKey, SignatureAlgorithm.HS256)
            .compact();
  }

  public Long extractUserId(String token) {
    Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
            SignatureAlgorithm.HS256.getJcaName());
    Claims claims = (Claims) Jwts.parser()
            .setSigningKey(hmacKey)
            .build().parse(token.substring(7)).getPayload();
    return claims.get("USER_ID", Long.class);
  }
}
