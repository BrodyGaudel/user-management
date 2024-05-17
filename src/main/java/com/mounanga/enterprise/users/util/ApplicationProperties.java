package com.mounanga.enterprise.users.util;


import java.util.List;

public interface ApplicationProperties {
    String getJwtSecret();
    Long getJwtExpiration();
    List<String> getAllowedOrigins();
    List<String> getAllowedHeaders();
    List<String> getExposedHeaders();
    List<String> getAllowedMethods();
    Boolean getAllowCredentials();
    Long getMaxAge();
    String getEmailSystem();
}
