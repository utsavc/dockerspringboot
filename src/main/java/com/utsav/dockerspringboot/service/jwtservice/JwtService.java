package com.utsav.dockerspringboot.service.jwtservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.expiration}")
    private String expiryTime;

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // Adding the users role in the claim
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // Extracting the authority (role) name
                .collect(Collectors.toList())); // Converting to a list
        // Creating the token with the claims and subject (username)
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        long expirationTime = Long.parseLong(expiryTime)*1000;
        return Jwts.builder()
                .setClaims(claims) // Include the claims (roles, etc.)
                .setSubject(subject) // Set the subject (username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set the issue time
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Set the expiration time
                .signWith(SignatureAlgorithm.HS256, secretKey) // Sign the token
                .compact(); // Build the token
    }



    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        System.out.println("userdetails: " + userDetails.getUsername() + " token username " +username );
        System.out.println(username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Boolean isTokenExpired(String token) {
        System.out.println("is Expired "+extractExpiration(token).before(new Date()) );
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        System.out.println("Token Claim "+Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody());
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
