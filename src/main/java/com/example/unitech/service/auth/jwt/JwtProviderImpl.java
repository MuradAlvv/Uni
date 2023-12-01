package com.example.unitech.service.auth.jwt;

import com.example.unitech.service.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import static com.example.unitech.persistence.metamodel.User_.ID;
import static com.example.unitech.persistence.metamodel.User_.PIN;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Component
@RequiredArgsConstructor
public class JwtProviderImpl implements JwtProvider {
    @Value("${security.jwt.secretKey}")
    private String secretKey;

    private Key key;

    @Value("${security.jwt.access-token-validity-in-seconds}")
    private Long accessTokenValidityInSeconds;
    private final UserService userService;

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

        val claims = new HashMap<String, String>();
        claims.put(PIN, pin);
        claims.put(ID, String.valueOf(userService.getByPin(pin).getId()));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(key, HS512)
                .setExpiration(expirationDate)
                .compact();
    }
}
