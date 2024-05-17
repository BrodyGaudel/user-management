package com.mounanga.enterprise.users.util;

import java.time.LocalDateTime;

public interface MailingService {
    void sendVerificationCode(String email, String code);
    void sendLoginNotification(String email, String fullName, LocalDateTime when, String where);
}
