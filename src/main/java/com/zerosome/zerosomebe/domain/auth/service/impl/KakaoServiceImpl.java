package com.zerosome.zerosomebe.domain.auth.service.impl;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.zerosome.zerosomebe.domain.auth.service.KakaoService;
import com.zerosome.zerosomebe.global.error.exception.SocialLoginFailedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoServiceImpl implements KakaoService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public String getKakaoData(String socialAccessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            headers.add("Authorization", "Bearer " + socialAccessToken);
            HttpEntity request = new HttpEntity<JsonArray>(headers);

            ResponseEntity<Object> responseData = restTemplate.exchange(
                    "https://kapi.kakao.com/v2/user/me", HttpMethod.GET, request, Object.class);

            return objectMapper.convertValue(responseData.getBody(), Map.class).get("id").toString();
        } catch (Exception e) {
            throw new SocialLoginFailedException(e.getMessage());
        }
    }
}
