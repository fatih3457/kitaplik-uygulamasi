package com.kitaplik.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}") // application.properties'den gizli anahtarı al
    private String secretKeyString;

    @Value("${jwt.expiration}") // application.properties'den geçerlilik süresini al
    private Long jwtExpiration;

    // Token'dan kullanıcı adını çıkaran metot
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Token oluşturma metodu
    public String generateToken(UserDetails userDetails) {
        return buildToken(userDetails, jwtExpiration);
    }

    // Token'ın geçerli olup olmadığını kontrol eden metot
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Token'ın süresinin dolup dolmadığını kontrol eder
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Token'dan son kullanma tarihini alır
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Token oluşturma mantığı
    private String buildToken(UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // Token'ın konusu (genellikle kullanıcı adı)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Başlangıç tarihi
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Bitiş tarihi
                .signWith(getSignInKey()) // İmzalama anahtarı
                .compact();
    }

    // Token'dan belirli bir "claim" (iddia) parçasını alır
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Token'daki tüm "claim"leri alır
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Gizli anahtarı oluşturur
    private SecretKey getSignInKey() {
        byte[] keyBytes = secretKeyString.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}