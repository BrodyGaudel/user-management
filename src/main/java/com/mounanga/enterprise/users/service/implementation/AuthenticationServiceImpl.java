package com.mounanga.enterprise.users.service.implementation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mounanga.enterprise.users.dto.request.AuthenticationRequest;
import com.mounanga.enterprise.users.dto.response.AuthenticationResponse;
import com.mounanga.enterprise.users.entity.Role;
import com.mounanga.enterprise.users.entity.User;
import com.mounanga.enterprise.users.entity.UserIam;
import com.mounanga.enterprise.users.entity.UserRoot;
import com.mounanga.enterprise.users.execption.ResourceNotFoundException;
import com.mounanga.enterprise.users.execption.UserNotEnabledException;
import com.mounanga.enterprise.users.repository.UserIamRepository;
import com.mounanga.enterprise.users.repository.UserRootRepository;
import com.mounanga.enterprise.users.service.AuthenticationService;
import com.mounanga.enterprise.users.util.ApplicationProperties;
import com.mounanga.enterprise.users.util.MailingService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final UserRootRepository userRootRepository;
    private final UserIamRepository userIamRepository;
    private final ApplicationProperties properties;
    private final MailingService mailingService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserRootRepository userRootRepository, UserIamRepository userIamRepository, ApplicationProperties properties, MailingService mailingService) {
        this.authenticationManager = authenticationManager;
        this.userRootRepository = userRootRepository;
        this.userIamRepository = userIamRepository;
        this.properties = properties;
        this.mailingService = mailingService;
    }

    private String generateToken(@NotNull User user, @NotNull List<String> roles) {
        Algorithm algorithm = Algorithm.HMAC256(properties.getJwtSecret());
        Date expiration = new Date(System.currentTimeMillis() + properties.getJwtExpiration());
        return JWT.create()
                .withSubject(user.getUsername())
                .withArrayClaim("roles", roles.toArray(new String[0]))
                .withClaim("fullName", user.getFullName())
                .withExpiresAt(expiration)
                .sign(algorithm);
    }

    private UserRoot findUserRootByUsername(@NotNull String username) {
        return userRootRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    private UserIam findUserIamByUsername(@NotNull String username) {
        return userIamRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    private List<String> getRoleNames(@NotNull List<Role> roles){
        return roles.stream().map(Role::getName).toList();
    }

    private void validateUserEnabled(@NotNull Boolean enabled){
        if(enabled.equals(Boolean.FALSE)){
            throw new UserNotEnabledException("user is not enabled");
        }
    }

    private LocalDateTime setUserLastLogin(@NotNull User user){
        user.setLastLogin(LocalDateTime.now());
        return switch (user) {
            case UserIam iam -> userIamRepository.save(iam).getLastLogin();
            case UserRoot root -> userRootRepository.save(root).getLastLogin();
            default -> throw new IllegalStateException("Unexpected user type: " + user.getClass().getSimpleName());
        };
    }

    private void sendLoginNotification(@NotNull String fullName, @NotNull String email, LocalDateTime when){
        mailingService.sendLoginNotification(email, fullName, when, "where");
    }


    @Override
    public AuthenticationResponse userRootAuthentication(@NotNull AuthenticationRequest request) {
        log.info("In userRootAuthentication()");
        Authentication authentication =  new UsernamePasswordAuthenticationToken(request.username(), request.password());
        authenticationManager.authenticate(authentication);
        UserRoot userRoot = findUserRootByUsername(request.username());
        validateUserEnabled(userRoot.getEnabled());
        List<String> roles = getRoleNames(userRoot.getRoles());
        LocalDateTime lastLogin = setUserLastLogin(userRoot);
        log.info("UserRoot with id '{}' is authenticated successfully at '{}'", userRoot.getId(),lastLogin);
        sendLoginNotification(userRoot.getFullName(), userRoot.getEmail(), lastLogin);
        return new AuthenticationResponse(request.username(), userRoot.getFullName(), generateToken(userRoot, roles));
    }

    @Override
    public AuthenticationResponse userIamAuthentication(@NotNull AuthenticationRequest request) {
        log.info("In userIamAuthentication()");
        Authentication authentication =  new UsernamePasswordAuthenticationToken(request.username(), request.password());
        authenticationManager.authenticate(authentication);
        UserIam userIam = findUserIamByUsername(request.username());
        validateUserEnabled(userIam.getEnabled());
        List<String> roles = getRoleNames(userIam.getRoles());
        LocalDateTime lastLogin = setUserLastLogin(userIam);
        log.info("UserIam with id '{}' is authenticated successfully at '{}'", userIam.getId(), lastLogin);
        return new AuthenticationResponse(request.username(), userIam.getFullName(), generateToken(userIam, roles));
    }


}
