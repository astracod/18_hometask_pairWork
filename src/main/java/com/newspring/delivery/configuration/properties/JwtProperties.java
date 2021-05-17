package com.newspring.delivery.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "auth.jwt")
public class JwtProperties {
    private String secret;
}
