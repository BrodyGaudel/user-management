package com.mounanga.enterprise.users.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VerificationRequest(
        @Email(message = "email is not well formatted")
        @NotBlank(message = "email is mandatory: it can not be blank")
        String email,
        @NotBlank(message = "code verification is mandatory: it can not be blank")
        @Size(min = 6, max = 6, message = "verification code must be 6 characters long")
        String code) {
}
