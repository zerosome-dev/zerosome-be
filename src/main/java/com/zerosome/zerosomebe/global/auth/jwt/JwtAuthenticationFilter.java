package com.zerosome.zerosomebe.global.auth.jwt;

import static com.zerosome.zerosomebe.global.auth.jwt.JwtValidationType.VALID_JWT;
import static io.jsonwebtoken.lang.Strings.hasText;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/*
 * JwtAuthenticationFilter
 * JWT 토큰을 검증하는 Spring Security 필터
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_HEADER = "Bearer ";
    private static final String BLANK = "";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            val token = getAccessTokenFromRequest(request);
            if (hasText(token) && jwtTokenProvider.validateToken(token) == VALID_JWT) {
                val authentication = new UserAuthentication(getMemberId(token), null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /*
     * getAccessTokenFromRequest
     * Request에서, Bearer를 제외한 토큰값(Access Token)을 추출하는 메소드
     * 토큰에 대한 인증을 거치기 전, 토큰값을 추출하는 사전 작업에 사용한다.
     */
    private String getAccessTokenFromRequest(HttpServletRequest request) {
        String auth = request.getHeader(AUTHORIZATION);
        return (auth != null && auth.startsWith(BEARER_HEADER)) ?
               request.getHeader(AUTHORIZATION).replaceFirst(BEARER_HEADER, BLANK) : null;
    }

    /*
     * getMemberId
     * 토큰에서 사용자 ID를 추출하는 메소드
     */
    private long getMemberId(String token) {
        return jwtTokenProvider.getMemberId(token);
    }
}