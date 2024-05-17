package com.mounanga.enterprise.users.dto.response;

public record AuthenticationResponse(String username, String fullName, String token) {
}
