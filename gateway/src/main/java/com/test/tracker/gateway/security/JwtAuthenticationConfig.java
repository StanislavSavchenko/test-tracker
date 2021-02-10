package com.test.tracker.gateway.security;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Data
@Builder
public class JwtAuthenticationConfig {

    private String url;
    private String header;
    private String prefix;
    private int expiration;
    private String secret;
}
