package com.mounanga.enterprise.users.util.implementation;

import com.mounanga.enterprise.users.util.ApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationPropertiesImpl implements ApplicationProperties {

    @Value("${application.security.jwt.secret}")
    private String jwtSecret;

    @Value("${application.security.jwt.time-expiration}")
    private Long jwtExpiration;

    @Value("${application.cors.allowed-origins}")
    private List<String> allowedOrigins;

    @Value("${application.cors.allowed-headers}")
    private List<String> allowedHeaders;

    @Value("${application.cors.exposed-headers}")
    private List<String> exposedHeaders;

    @Value("${application.cors.allowed-methods}")
    private List<String> allowedMethods;

    @Value("${application.cors.allow-credentials}")
    private Boolean allowCredentials;

    @Value("${application.cors.max-age}")
    private Long maxAge;

    @Value("${application.mail.email-system}")
    private String emailSystem;

    @Override
    public String getJwtSecret() {
        return jwtSecret;
    }

    @Override
    public Long getJwtExpiration() {
        return jwtExpiration;
    }

    @Override
    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    @Override
    public List<String> getAllowedHeaders() {
        return allowedHeaders;
    }

    @Override
    public List<String> getExposedHeaders() {
        return exposedHeaders;
    }

    @Override
    public List<String> getAllowedMethods() {
        return allowedMethods;
    }

    @Override
    public Boolean getAllowCredentials() {
        return allowCredentials;
    }

    @Override
    public Long getMaxAge() {
        return maxAge;
    }

    @Override
    public String getEmailSystem() {
        return emailSystem;
    }
}
