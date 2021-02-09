package com.test.tracker.core.service;

import com.test.tracker.core.model.dto.InfoServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class AdditionalUserInfoService {

    private static final String INFO_SERVICE_URI = "user/%s/rating";

    private final RestTemplate restTemplate;
    private final String infoServiceUrl;

    @Autowired
    public AdditionalUserInfoService(RestTemplate restTemplate, Environment env) {
        this.restTemplate = restTemplate;
        infoServiceUrl = env.getProperty("TRACKER_INFO_URL");
    }

    public Map<Long, InfoServiceResponse> getUserInfo(Set<Long> userIdSet) {
        Map<Long, InfoServiceResponse> responseMap = new HashMap<>();
        userIdSet.forEach(id -> {
            String uri = String.format(INFO_SERVICE_URI, id);
            ResponseEntity<InfoServiceResponse> response = null;
            try {
                response = restTemplate.getForEntity(infoServiceUrl + uri, InfoServiceResponse.class);
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                log.error("======== [getUserInfo] Exception e = {}", e.getMessage());
                responseMap.put(id, InfoServiceResponse.builder()
                        .rating(3.0f)
                        .build());
            }
            responseMap.put(id, response.getBody());
        });

        return responseMap;
    }
}