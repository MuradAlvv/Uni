package com.example.unitech.configuration.security;

import com.example.unitech.service.auth.jwt.JwtParser;
import com.example.unitech.util.ErrorResponseHandler;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            @Autowired JwtParser jwtParser,
            @Autowired ErrorResponseHandler errorResponseHandler) {
        http.authorizeHttpRequests(
                        (requests) ->
                                requests.requestMatchers(
                                                "/api/v1/users/**",
                                                "/api/v1/auth/**",
                                                "/v3/**",
                                                "/swagger-ui/**")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated()
                                        .and()
                                        .addFilterBefore(
                                                new AuthenticationFilter(
                                                        jwtParser, errorResponseHandler),
                                                UsernamePasswordAuthenticationFilter.class))
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
