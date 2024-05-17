package com.mounanga.enterprise.users.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank(message = "username is mandatory: it can not be blank") String username,
        @NotBlank(message = "password is mandatory: it can not be blank") String password) {
}
