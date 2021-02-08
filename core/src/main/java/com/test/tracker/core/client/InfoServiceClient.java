package com.test.tracker.core.client;

import com.test.tracker.core.model.dto.InfoServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class InfoServiceClient {

    private final Float DEFAULT_RATING = 3.0f;
    private final RestTemplate template;
    private final String infoServiceUrl;

    public InfoServiceClient(Environment env) {
        template = new RestTemplate();
        infoServiceUrl = env.getProperty("TRACKER_SERVICE_INFO_URL");
    }

    public InfoServiceResponse getRating(Long userId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", String.valueOf(userId));
        UriBuilder builder = UriBuilder.fromPath(infoServiceUrl);
        URI uri = builder.buildFromMap(parameters);
        try {
            return template.getForEntity(uri, InfoServiceResponse.class).getBody();
        } catch (RestClientException e) {
            log.error("Unable to get response from info service");
            return getDefaultRating();
        }

    }

    private InfoServiceResponse getDefaultRating() {
        return InfoServiceResponse.builder()
                .rating(DEFAULT_RATING)
                .build();
    }

}
