package com.zerosome.zerosomebe.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.zerosome.zerosomebe.global.auth.jwt.JwtAuthenticationEntryPoint;
import com.zerosome.zerosomebe.global.auth.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
                .rememberMe(AbstractHttpConfigurer::disable) // rememberMe 비활성화
                .logout(AbstractHttpConfigurer::disable) // logout 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 기본 로그인 페이지(form 기반 로그인) 없애기
                .httpBasic(AbstractHttpConfigurer::disable) // httpBasic 인증 없애기
                .sessionManagement(sessionManagement ->
                                           sessionManagement.sessionCreationPolicy(
                                                   SessionCreationPolicy.STATELESS)) // 세션 사용하지 않음
                .exceptionHandling(exceptionHandling ->
                                           exceptionHandling.authenticationEntryPoint(
                                                   jwtAuthenticationEntryPoint)) // 인증 실패시, JwtAuthenticationEntryPoint에서 처리
                .authorizeHttpRequests(authorizeHttpRequests ->
                                               authorizeHttpRequests
                                                       .requestMatchers(
                                                               new AntPathRequestMatcher("/api/v1/auth"),
                                                               new AntPathRequestMatcher(
                                                                       "/api/v1/auth/nickname"),
                                                               new AntPathRequestMatcher("/api/v1/auth/join"),
                                                               new AntPathRequestMatcher("/api/app/v1/home/*"),
                                                               new AntPathRequestMatcher(
                                                                       "/api/app/v1/category/*"),
                                                               new AntPathRequestMatcher(
                                                                       "/api/app/v1/filter/*"),
                                                               new AntPathRequestMatcher(
                                                                       "/api/app/v1/product/category/*"),
                                                               new AntPathRequestMatcher(
                                                                       "/api/app/v1/product/detail/{id}"),
                                                               new AntPathRequestMatcher(
                                                                       "/api/app/v1/review/{id}", "GET"),
                                                               new AntPathRequestMatcher("/api/dev-util"),
                                                               new AntPathRequestMatcher(
                                                                       "/api/local/datadump/*"))
                                                       .permitAll()
                                                       .requestMatchers(new AntPathRequestMatcher("/error"))
                                                       .permitAll()
                                                       .anyRequest()
                                                       .authenticated()) // /service/v1/auth/**, /error는 인증 없이 접근 가능
                .addFilterBefore(jwtAuthenticationFilter,
                                 UsernamePasswordAuthenticationFilter.class) // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 수행
                .build();
    }
}