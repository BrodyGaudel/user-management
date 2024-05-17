package com.mounanga.enterprise.users.service;

import com.mounanga.enterprise.users.dto.request.AuthenticationRequest;
import com.mounanga.enterprise.users.dto.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse userRootAuthentication(AuthenticationRequest request);
    AuthenticationResponse userIamAuthentication(AuthenticationRequest request);
}
