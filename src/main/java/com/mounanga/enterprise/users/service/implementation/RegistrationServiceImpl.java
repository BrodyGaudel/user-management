package com.mounanga.enterprise.users.service.implementation;

import com.mounanga.enterprise.users.dto.request.PwdRequest;
import com.mounanga.enterprise.users.dto.request.UserRootRequest;
import com.mounanga.enterprise.users.dto.request.ValidationRequest;
import com.mounanga.enterprise.users.dto.request.VerificationRequest;
import com.mounanga.enterprise.users.entity.Role;
import com.mounanga.enterprise.users.entity.UserRoot;
import com.mounanga.enterprise.users.entity.Verification;
import com.mounanga.enterprise.users.entity.Workspace;
import com.mounanga.enterprise.users.execption.*;
import com.mounanga.enterprise.users.repository.RoleRepository;
import com.mounanga.enterprise.users.repository.UserRootRepository;
import com.mounanga.enterprise.users.repository.VerificationRepository;
import com.mounanga.enterprise.users.service.RegistrationService;
import com.mounanga.enterprise.users.util.MailingService;
import com.mounanga.enterprise.users.util.Mappers;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationServiceImpl.class);

    private final UserRootRepository userRootRepository;
    private final RoleRepository roleRepository;
    private final VerificationRepository verificationRepository;
    private final MailingService mailingService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationServiceImpl(UserRootRepository userRootRepository, RoleRepository roleRepository, VerificationRepository verificationRepository, MailingService mailingService, PasswordEncoder passwordEncoder) {
        this.userRootRepository = userRootRepository;
        this.roleRepository = roleRepository;
        this.verificationRepository = verificationRepository;
        this.mailingService = mailingService;
        this.passwordEncoder = passwordEncoder;
    }

    private void validateUniqueUserCredentials(@NotNull String username, @NotNull String email){
        if (userRootRepository.existsByUsername(username)){
            throw new ResourceAlreadyExistException("username already exist");
        }
        if(userRootRepository.existsByEmail(email)){
            throw new ResourceAlreadyExistException("email already exist");
        }
    }

    private @NotNull List<Role> findAllRoles(){
        return roleRepository.findAll();
    }

    private @NotNull String generateVerificationCode() {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i <6; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    private void sendVerificationCode(@NotNull String email) {
        Verification savedVerification = saveVerificationCode(email);
        mailingService.sendVerificationCode(savedVerification.getEmail(), savedVerification.getCode());
    }

    private @NotNull Verification saveVerificationCode(@NotNull String email) {
        Verification verification = new Verification();
        verification.setEmail(email);
        verification.setCode(generateVerificationCode());
        verification.setExpiryDateTime(LocalDateTime.now().plusMinutes(5));
        return verificationRepository.save(verification);
    }

    private UserRoot findUserRootByEmail(@NotNull String email) {
      return userRootRepository.findByEmail(email)
              .orElseThrow( () -> new ResourceNotFoundException("user not found"));
    }

    private @NotNull UserRoot enabledUserRoot(String email, String verificationId) {
        UserRoot root = findUserRootByEmail(email);
        root.setEnabled(true);
        UserRoot savedRoot = userRootRepository.save(root);
        verificationRepository.deleteById(verificationId);
        return savedRoot;
    }

    private Verification findByEmailAndByCode(String email, String code){
        return verificationRepository.findByEmailAndCode(email, code)
                .orElseThrow( () -> new ResourceNotFoundException("verification code not found"));
    }

    private @NotNull UserRoot modifyPassword(String email, String newPassword, String verificationId){
        verificationRepository.deleteById(verificationId);
        UserRoot root = findUserRootByEmail(email);
        root.setPassword(passwordEncoder.encode(newPassword));
        root.setEnabled(true);
        return userRootRepository.save(root);
    }

    @Transactional
    @Override
    public void register(@NotNull UserRootRequest request) {
        log.info("In register()");
        validateUniqueUserCredentials(request.getUsername(), request.getEmail());
        UserRoot root = Mappers.fromUserRootRequest(request);
        root.setRoles(findAllRoles());
        root.setEnabled(false);
        root.setPassword(passwordEncoder.encode(request.getPassword()));
        Workspace workspace = root.getWorkspace();
        workspace.setUserRoot(root);
        root.setWorkspace(workspace);
        UserRoot savedRoot = userRootRepository.save(root);
        log.info("user root saved with id '{}' at '{}'", savedRoot.getId(), savedRoot.getCreatedDate());
        sendVerificationCode(savedRoot.getEmail());
    }

    @Override
    public void validate(@NotNull VerificationRequest request) {
        log.info("In validate()");
        Verification verification = findByEmailAndByCode(request.email(), request.code());
        if (verification.getExpiryDateTime().isBefore(LocalDateTime.now())) {
            verificationRepository.delete(verification);
            sendVerificationCode(request.email());
            throw new VerificationCodeExpiredException("verification code is expired: a new code has been sent");
        }else {
            UserRoot enabledRoot = enabledUserRoot(verification.getEmail(), verification.getId());
            log.info("user root with id '{}' has been enabled at '{}'", enabledRoot.getId(), enabledRoot.getLastModifiedDate());
        }
    }

    @Override
    public void askValidity(@NotNull ValidationRequest request) {
        log.info("In askValidity()");
        UserRoot root = findUserRootByEmail(request.email());
        verificationRepository.deleteAllByEmail(root.getEmail());
        sendVerificationCode(request.email());
        log.info("user root with id '{}' has been asked", root.getId());
    }

    @Override
    public void updatePassword(@NotNull PwdRequest request) {
        log.info("In updatePassword()");
        if(request.newPassword().equals(request.confirmPassword())){
            Verification verification =  findByEmailAndByCode(request.email(), request.code());
            if (verification.getExpiryDateTime().isBefore(LocalDateTime.now())) {
                verificationRepository.delete(verification);
                sendVerificationCode(request.email());
                throw new VerificationCodeExpiredException("verification code is expired");
            }else{
                UserRoot updatedRoot = modifyPassword(verification.getEmail(), request.newPassword(), verification.getId());
                log.info("password successfully updated for user with id '{}'",updatedRoot.getId());
            }
        }else{
            throw new IllegalArgumentException("password does not match");
        }

    }
}
