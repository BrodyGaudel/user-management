package com.mounanga.enterprise.users.service;

import com.mounanga.enterprise.users.dto.request.PwdRequest;
import com.mounanga.enterprise.users.dto.request.UserRootRequest;
import com.mounanga.enterprise.users.dto.request.ValidationRequest;
import com.mounanga.enterprise.users.dto.request.VerificationRequest;

public interface RegistrationService {
    void register(UserRootRequest request);
    void validate(VerificationRequest request);
    void askValidity(ValidationRequest request);
    void updatePassword(PwdRequest request);
}
