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
                .csrf().disable() // CSRF 보호 비활성화
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



/*
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // JWT 기반이므로 CSRF 비활성화
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 무상태 세션 정책 설정
                .and()
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/auth/**", "/public/**").permitAll() // 특정 경로에 대해 접근 허용
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // JWT 필터 추가

        return http.build();
    }


    // CORS 설정이 필요하다면 추가
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization"); // 헤더 노출 설정
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
*/