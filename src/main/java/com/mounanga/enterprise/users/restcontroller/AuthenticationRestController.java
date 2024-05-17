package com.mounanga.enterprise.users.restcontroller;

import com.mounanga.enterprise.users.dto.request.*;
import com.mounanga.enterprise.users.dto.response.AuthenticationResponse;
import com.mounanga.enterprise.users.service.AuthenticationService;
import com.mounanga.enterprise.users.service.RegistrationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationRestController {

    private final AuthenticationService authenticationService;
    private final RegistrationService registrationService;

    public AuthenticationRestController(AuthenticationService authenticationService, RegistrationService registrationService) {
        this.authenticationService = authenticationService;
        this.registrationService = registrationService;
    }

    @PostMapping("/login-root")
    public AuthenticationResponse userRootAuthentication(@RequestBody AuthenticationRequest request){
        return authenticationService.userRootAuthentication(request);
    }

    @PostMapping("/login-iam")
    public AuthenticationResponse userIamAuthentication(@RequestBody AuthenticationRequest request){
        return authenticationService.userIamAuthentication(request);
    }

    @PostMapping("/register")
    public void register(@RequestBody UserRootRequest request){
        registrationService.register(request);
    }

    @PostMapping("/validate")
    public void validate(@RequestBody VerificationRequest request){
        registrationService.validate(request);
    }

    @PostMapping("/ask-validate")
    public void askValidation(@RequestBody ValidationRequest request){
        registrationService.askValidity(request);
    }

    @PostMapping("/modify-password")
    public void updatePassword(@RequestBody PwdRequest request){
        registrationService.updatePassword(request);
    }
}