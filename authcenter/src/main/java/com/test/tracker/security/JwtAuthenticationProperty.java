package com.test.tracker.security;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Data
@Builder
public class JwtAuthenticationProperty {

    private String url;
    private String header;
    private String prefix;
    private int expiration;
    private String secret;

}
