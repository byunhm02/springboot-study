package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/index.html","main.html","/set-nickname","/auth/","/auth/login","/member/","/member/save","/callback","/login","/public/**","/error","/api/kakao/**").permitAll() // 루트 경로와 index.html에 대한 접근 허용
                        .anyRequest().authenticated()                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .permitAll()
                );
        return http.build();
    }
}
