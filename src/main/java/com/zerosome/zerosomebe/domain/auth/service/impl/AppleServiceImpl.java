package com.zerosome.zerosomebe.domain.auth.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zerosome.zerosomebe.domain.auth.service.AppleService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppleServiceImpl implements AppleService {

    private static final String TOKEN_VALUE_DELIMITER = "\\.";
    private static final String MODULUS = "n";
    private static final String EXPONENT = "e";
    private static final int QUOTES = 1;
    private static final int POSITIVE_NUMBER = 1;

    @Override
    public String getAppleData(String socialAccessToken) throws JsonProcessingException {
        JsonArray publicKeyList = getApplePublicKeys();
        PublicKey publicKey = makePublicKey(socialAccessToken, publicKeyList);

        Claims userInfo = Jwts.parserBuilder()
                              .setSigningKey(publicKey)
                              .build()
                              .parseClaimsJws(socialAccessToken.replaceFirst("Bearer ", ""))
                              .getBody();

        JsonObject userInfoObject = (JsonObject) JsonParser.parseString(new Gson().toJson(userInfo));
        return userInfoObject.get("sub").getAsString();
    }

    private JsonArray getApplePublicKeys() {
        try {
            URL url = new URL("https://appleid.apple.com/auth/keys");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HttpMethod.GET.name());

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuilder result = splitHttpResponse(bufferedReader);

            JsonObject keys = (JsonObject) JsonParser.parseString(result.toString());
            return (JsonArray) keys.get("keys");

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private StringBuilder splitHttpResponse(BufferedReader bufferedReader) {
        try {
            StringBuilder result = new StringBuilder();

            String line;
            while (Objects.nonNull(line = bufferedReader.readLine())) {
                result.append(line);
            }
            bufferedReader.close();

            return result;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private Map<String, String> getHeaders(String accessToken) throws JsonProcessingException {
        String header = accessToken.split(TOKEN_VALUE_DELIMITER)[0];
        System.out.println("HEADER : " + header);
        String decodedHdr = new String(Base64.getDecoder().decode(header), StandardCharsets.UTF_8);
        return new ObjectMapper().readValue(decodedHdr, Map.class);
    }

    private PublicKey makePublicKey(String accessToken, JsonArray publicKeyList)
            throws JsonProcessingException {
        Map<String, String> headers = getHeaders(accessToken);
        JsonObject matchingPublicKey = findMatchingPublicKey(publicKeyList,
                                                             headers.get("kid"), headers.get("alg"));

        if (Objects.isNull(matchingPublicKey)) {
            throw new IllegalArgumentException();
        }

        return getPublicKey(matchingPublicKey);
    }

    private JsonObject findMatchingPublicKey(JsonArray publicKeyList, String kid, String alg) {
        System.out.println("kid = " + kid);
        System.out.println("alg = " + alg);
        for (JsonElement publicKey : publicKeyList) {
            JsonObject publicKeyObject = publicKey.getAsJsonObject();
            String publicKid = publicKeyObject.get("kid").toString().replaceAll("\"", "");
            String publicAlg = publicKeyObject.get("alg").toString().replaceAll("\"", "");
            System.out.println("publicKid = " + publicKid);
            System.out.println("publicAlg = " + publicAlg);

            if (kid.equals(publicKid) && alg.equals(publicAlg)) {
                return publicKeyObject;
            }
        }

        return null;
    }

    private PublicKey getPublicKey(JsonObject object) {
        try {
            String modulus = object.get(MODULUS).toString();
            String exponent = object.get(EXPONENT).toString();

            byte[] modulusBytes = Base64.getUrlDecoder().decode(
                    modulus.substring(QUOTES, modulus.length() - QUOTES));
            byte[] exponentBytes = Base64.getUrlDecoder().decode(
                    exponent.substring(QUOTES, exponent.length() - QUOTES));

            BigInteger modulusValue = new BigInteger(POSITIVE_NUMBER, modulusBytes);
            BigInteger exponentValue = new BigInteger(POSITIVE_NUMBER, exponentBytes);

            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulusValue, exponentValue);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException exception) {
            throw new IllegalStateException();
        }
    }
}
