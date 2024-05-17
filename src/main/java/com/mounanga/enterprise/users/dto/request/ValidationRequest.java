package com.mounanga.enterprise.users.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ValidationRequest(
        @Email(message = "email is not well formatted")
        @NotBlank(message = "email is mandatory: it can not be blank")
        String email) {
}
