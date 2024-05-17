package com.mounanga.enterprise.users.dto.request;

public record PwdRequest(String newPassword, String confirmPassword, String code, String email) {
}
