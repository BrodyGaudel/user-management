package com.mounanga.enterprise.users.service.implementation;

import com.mounanga.enterprise.users.dto.request.UserRootRequest;
import com.mounanga.enterprise.users.dto.response.UserRootResponse;
import com.mounanga.enterprise.users.entity.UserRoot;
import com.mounanga.enterprise.users.execption.ResourceNotFoundException;
import com.mounanga.enterprise.users.repository.UserRootRepository;
import com.mounanga.enterprise.users.service.UserRootService;
import com.mounanga.enterprise.users.util.Mappers;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRootServiceIml implements UserRootService {

    private static final Logger log = LoggerFactory.getLogger(UserRootServiceIml.class);

    private final UserRootRepository userRootRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRootServiceIml(UserRootRepository userRootRepository, PasswordEncoder passwordEncoder) {
        this.userRootRepository = userRootRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private String getUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails userDetails){
            return userDetails.getUsername();
        }
        return principal.toString();
    }

    private UserRoot findUserRootByUsername(String username){
        return userRootRepository.findByUsername(username)
                .orElseThrow( () -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserRootResponse getUserRoot() {
        log.info("In getUserRoot()");
        UserRoot userRoot = findUserRootByUsername(getUsername());
        log.info("user root found");
        return Mappers.fromUserRoot(userRoot);
    }

    @Override
    public UserRootResponse updateUserRoot(@NotNull UserRootRequest request) {
        log.info("In updateUserRoot()");
        UserRoot userRoot = findUserRootByUsername(request.getUsername());
        userRoot.setFirstname(request.getFirstname());
        userRoot.setLastname(request.getLastname());
        userRoot.setDateOfBirth(request.getDateOfBirth());
        userRoot.setPlaceOfBirth(request.getPlaceOfBirth());
        userRoot.setNationality(request.getNationality());
        userRoot.setGender(request.getGender());
        userRoot.setPassword(passwordEncoder.encode(request.getPassword()));
        userRoot.getWorkspace().setDescription(request.getWorkspace().getDescription());
        userRoot.getWorkspace().setName(request.getWorkspace().getName());
        UserRoot rootUpdated = userRootRepository.save(userRoot);
        log.info("user root with id {} updated at '{}", rootUpdated.getId(), rootUpdated.getLastModifiedDate());
        return Mappers.fromUserRoot(rootUpdated);
    }

    @Override
    public void deleteUserRoot() {
        log.info("In deleteUserRoot()");
        UserRoot userRoot = findUserRootByUsername(getUsername());
        userRootRepository.delete(userRoot);
    }


}
