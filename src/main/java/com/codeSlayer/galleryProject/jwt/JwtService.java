package com.codeSlayer.galleryProject.jwt;

import com.codeSlayer.galleryProject.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {


    @Autowired
    UserRepository userRepository;

    public static final String SECRET_KEY = "GOsYeyo5AXbmvx3oQXthuETjmlXQ8cEWDzK7nI/WzSE=";

    public String generateToken(UserDetails userDetails, HttpServletRequest request,String role) {
        System.out.println(request);
        Map<String, Object> claims = new HashMap<>();
        claims.put("ROLE", role);
        claims.put("ip address",request.getRemoteAddr());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }
    public <T> T exportToken(String token, Function<Claims, T> claimsFunction) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
        return claimsFunction.apply(claims);
    }
    public String getUsername(String token) {
        return exportToken(token, Claims::getSubject);
    }
    public boolean isTokenExpired(String token) {
        return new Date().before(exportToken(token, Claims::getExpiration));
    }


    public Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String getMapAttributeFromToken(String token,String key) {
        return exportToken(token, claims -> claims.get(key, String.class));
    }
    public String getRoleFromUser(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        return getMapAttributeFromToken(token,"ROLE");
    }
    public String getTokenFromRequest(HttpServletRequest request) {
        String header;
        String token;
        header = request.getHeader("Authorization");
        token = header.substring(7);
        return token;
    }
    public Long getIdFromToken(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        String username = getUsername(token);
        return userRepository.getIdByUsername(username);
    }
}