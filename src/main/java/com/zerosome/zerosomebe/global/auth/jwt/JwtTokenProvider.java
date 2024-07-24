package com.zerosome.zerosomebe.global.auth.jwt;

import static io.jsonwebtoken.Header.JWT_TYPE;
import static io.jsonwebtoken.Header.TYPE;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.util.Base64.getEncoder;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.zerosome.zerosomebe.global.config.ValueConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * JwtTokenProvider
 * JWT 토큰을 생성하고 검증하는 클래스
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final ValueConfig valueConfig;

    /*
     * generateToken
     * 토큰을 생성하는 메소드
     * Authentication에서 사용자 정보를 추출하고, expiration(토큰 만료시간)을 설정한 후, jwt를 생성한다.
     */
    public String generateToken(Authentication authentication, long expiration) {
        return Jwts.builder()
                   .setHeaderParam(TYPE, JWT_TYPE)
                   .setClaims(generateClaims(authentication))
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + expiration))
                   .signWith(getSigningKey())
                   .compact();
    }

    /*
     * generateClaims
     * 사용자 정보를 추출하여 Claims로 반환하는 메소드
     */
    private Claims generateClaims(Authentication authentication) {
        Claims claims = Jwts.claims();
        claims.put("memberId", authentication.getPrincipal());
        return claims;
    }

    /*
     * getSigningKey
     * jwt 토큰을 서명할 때 필요한 secretKey를 생성하는 메소드
     */
    private SecretKey getSigningKey() {
        String encodedKey = getEncoder().encodeToString(valueConfig.getSecretKey().getBytes());
        return hmacShaKeyFor(encodedKey.getBytes());
    }

    /*
     * validateToken
     * JWT 토큰을 검증하는 메소드
     */
    public JwtValidationType validateToken(String token) {
        try {
            getBody(token);
            return JwtValidationType.VALID_JWT;
        } catch (MalformedJwtException exception) {
            log.error(exception.getMessage());
            return JwtValidationType.INVALID_JWT_TOKEN;
        } catch (ExpiredJwtException exception) {
            log.error(exception.getMessage());
            return JwtValidationType.EXPIRED_JWT_TOKEN;
        } catch (UnsupportedJwtException exception) {
            log.error(exception.getMessage());
            return JwtValidationType.UNSUPPORTED_JWT_TOKEN;
        } catch (IllegalArgumentException exception) {
            log.error(exception.getMessage());
            return JwtValidationType.EMPTY_JWT;
        }
    }

    /*
     * getBody
     * JWT 토큰에서 Claims 정보를 추출하는 메소드
     */
    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSigningKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    /*
     * getMemberId
     * JWT 토큰에서 memberId를 추출하는 메소드
     */
    public Long getMemberId(String token) {
        Claims claims = getBody(token);
        return Long.parseLong(claims.get("memberId").toString());
    }
}
