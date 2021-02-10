package com.test.tracker.core.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class AdditionalUserInfoService {

    private static final Float DEFAULT_RATING = 3.0f;

    private final RestTemplate restTemplate;
    private final String infoServiceUrl;
    private final ObjectMapper objectMapper;

    @Autowired
    public AdditionalUserInfoService(RestTemplate restTemplate, Environment env, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        infoServiceUrl = env.getProperty("TRACKER_INFO_URL");
        this.objectMapper = objectMapper;
    }

    @SneakyThrows(IOException.class)
    public Map<Long, Float> getUsersInfo(Set<Long> userIdList) {
        URI uri = URI.create(infoServiceUrl + "/user/rating");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        ArrayNode request = (ArrayNode) objectMapper.readTree(objectMapper.writeValueAsString(userIdList));

        HttpEntity<String> entity = new HttpEntity<>(
                request.toString()
                , headers
        );

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
            return objectMapper.readValue(responseEntity.getBody(), new TypeReference<HashMap<Long, Float>>() {
            });
        } catch (RestClientException e) {
            log.error("Unable to get response from info service");
            return getDefaultRating(userIdList);
        }
    }

    private Map<Long, Float> getDefaultRating(Set<Long> userIdList) {
        Map<Long, Float> response = new HashMap<>();
        userIdList.forEach(userId -> response.put(userId, DEFAULT_RATING));
        return response;

    }

}