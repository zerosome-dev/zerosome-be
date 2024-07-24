package com.zerosome.zerosomebe.domain.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface AppleService {
    public String getAppleData(String socialAccessToken) throws JsonProcessingException;
}
