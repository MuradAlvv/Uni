package com.example.unitech.service.auth;

import static com.example.unitech.persistence.metamodel.User_.PIN;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtProviderImpl implements JwtProvider {
    @Value("${security.jwt.secretKey}")
    private String secretKey;

    private Key key;

    @Value("${security.jwt.access-token-validity-in-seconds}")
    private Long accessTokenValidityInSeconds;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @Override
    public String generateToken(Authentication authentication) {
        val userDetails = (UserDetails) authentication.getPrincipal();
        val pin = userDetails.getUsername();
        val expirationDate =
                new Date(new Date().getTime() + accessTokenValidityInSeconds * 60 * 24);

        return Jwts.builder()
                .claim(PIN, pin)
                .signWith(key, HS512)
                .setExpiration(expirationDate)
                .compact();
    }

    @Override
    public Authentication getAuthentication(String token) {
        val claims = parseClaims(token);
        val pin = claims.get(PIN, String.class);
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        UserDetails userDetails = new User(pin, "", authorityList);

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorityList);
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
