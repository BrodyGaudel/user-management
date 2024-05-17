package com.mounanga.enterprise.users.service.implementation;

import com.mounanga.enterprise.users.dto.model.PageModel;
import com.mounanga.enterprise.users.dto.request.UserIamRequest;
import com.mounanga.enterprise.users.dto.request.UserRoleRequest;
import com.mounanga.enterprise.users.dto.response.UserIamResponse;
import com.mounanga.enterprise.users.entity.Role;
import com.mounanga.enterprise.users.entity.UserIam;
import com.mounanga.enterprise.users.entity.Workspace;
import com.mounanga.enterprise.users.execption.*;
import com.mounanga.enterprise.users.repository.RoleRepository;
import com.mounanga.enterprise.users.repository.UserIamRepository;
import com.mounanga.enterprise.users.repository.WorkspaceRepository;
import com.mounanga.enterprise.users.service.UserIamService;
import com.mounanga.enterprise.users.util.Mappers;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserIamServiceImpl implements UserIamService {


    private static final Logger log = LoggerFactory.getLogger(UserIamServiceImpl.class);

    private final UserIamRepository userIamRepository;
    private final WorkspaceRepository workspaceRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserIamServiceImpl(UserIamRepository userIamRepository, WorkspaceRepository workspaceRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userIamRepository = userIamRepository;
        this.workspaceRepository = workspaceRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    public UserIamResponse getUserIamById(String id) {
        log.info("In getUserIamById()");
        UserIam userIam = findUserIamById(id);
        log.info("user found with id '{}' ",userIam.getId());
        return Mappers.fromUserIAM(userIam);
    }

    @Override
    public UserIamResponse getCurrentUserIam() {
        log.info("In getCurrentUserIam()");
        UserIam iam = findUserIamByUsername(getCurrentUsername());
        log.info("current user found");
        return Mappers.fromUserIAM(iam);
    }

    @Override
    public PageModel<UserIamResponse> getUserIamByPage(int page, int pageSize) {
        log.info("In getUserIamByPage()");
        Page<UserIam> iamPage = userIamRepository.findAllByWorkspaceId(
                getCurrentWorkspaceId(), PageRequest.of(page, pageSize)
        );
        log.info("{} users found", iamPage.getTotalElements());
        return fromUserIamPage(iamPage, page);
    }

    @Override
    public PageModel<UserIamResponse> searchUserIamByPage(String keyword, int page, int pageSize) {
        log.info("In searchUserIamByPage()");
        Page<UserIam> iamPage = userIamRepository.searchAllByWorkspaceId(
                getCurrentWorkspaceId(), "%"+keyword+"%",PageRequest.of(page, pageSize)
        );
        log.info("{} user(s) found", iamPage.getTotalElements());
        return fromUserIamPage(iamPage, page);
    }

    @Override
    public UserIamResponse createUserIam(@NotNull UserIamRequest request) {
        log.info("In createUserIam()");
        checkIfUsernameIsUnique(request.getUsername());
        UserIam iam = Mappers.fromUserIamRequest(request);
        iam.setPassword(passwordEncoder.encode(request.getPassword()));
        iam.setWorkspace(getCurrentWorkspace());
        iam.setRoles(getUserRoleAsList());
        iam.setEnabled(false);
        UserIam iamSaved = userIamRepository.save(iam);
        log.info("user iam saved successfully with id '{}' , at '{}'", iamSaved.getId(), iamSaved.getCreatedDate());
        return Mappers.fromUserIAM(iamSaved);
    }

    @Override
    public UserIamResponse updateUserIam(@NotNull UserIamRequest request) {
        log.info("");
        UserIam iam = findUserIamById(request.getId());
        String workspaceId = iam.getWorkspace().getId();
        if(workspaceId.equals(iam.getWorkspace().getId())) {
            iam.setPassword(passwordEncoder.encode(request.getPassword()));
            iam.setFirstname(request.getFirstname());
            iam.setLastname(request.getLastname());

            UserIam iamUpdated = userIamRepository.save(iam);
            log.info("iam with id '{}' updated with at '{}'", iamUpdated.getId(), iamUpdated.getCreatedDate());
            return Mappers.fromUserIAM(iamUpdated);
        }
        throw new NotAuthorizedException("Not authorized");
    }

    @Override
    public void deleteUserIamById(String id) {
        log.info("In deleteUserIamById()");
        UserIam iam = findUserIamById(id);
        String workspaceId = getCurrentWorkspaceId();
        if(iam.getWorkspace().getId().equals(workspaceId)){
            userIamRepository.deleteById(iam.getId());
            log.info("user iam with id '{}' deleted",iam.getId());
        }else{
            throw new NotAuthorizedException("you are not authorized to delete this id");
        }
    }

    @Override
    public void deleteAllUserIam() {
        log.info("In deleteAllUserIam()");
        String workspaceId = getCurrentWorkspaceId();
        List<UserIam> iamList = userIamRepository.findByWorkspaceId(workspaceId);
        userIamRepository.deleteAll(iamList);
        log.info(" all user iam(s) with workspace id '{}' deleted",workspaceId);
    }

    @Transactional
    @Override
    public void addRoleToUserIam(@NotNull UserRoleRequest request) {
        log.info("In addRoleToUserIam()");
        UserIam iam = findUserIamById(request.userId());
        Role role = findRoleByName(request.roleName());
        iam.getRoles().add(role);
        userIamRepository.save(iam);
        log.info("role '{}' added to user with id '{}'", role.getName(), iam.getId());
    }

    @Transactional
    @Override
    public void removeRoleFromUserIam(@NotNull UserRoleRequest request) {
        log.info("In removeRoleFromUserIam()");
        UserIam iam = findUserIamById(request.userId());
        Role role = findRoleByName(request.roleName());
        iam.getRoles().remove(role);
        userIamRepository.save(iam);
        log.info("role '{}' removed from user with id '{}'", role.getName(), iam.getId());
    }

    private UserIam findUserIamById(String id) {
        return userIamRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
    }

    private UserIam findUserIamByUsername(String username){
        return userIamRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
    }

    private String getCurrentUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails userDetails){
            return userDetails.getUsername();
        }
        return principal.toString();
    }

    private String getCurrentWorkspaceId(){
        String currentUsername = getCurrentUsername();
        return workspaceRepository.findByUsername(currentUsername).orElseThrow(
                () -> new ResourceNotFoundException("workspace not found")
        ).getId();
    }

    private Workspace getCurrentWorkspace(){
        String currentUsername = getCurrentUsername();
        return workspaceRepository.findByUsername(currentUsername).orElseThrow(
                () -> new ResourceNotFoundException("workspace not found")
        );
    }

    private void checkIfUsernameIsUnique(String username){
        if(userIamRepository.existsByUsername(username)){
            throw new ResourceAlreadyExistException("username");
        }
    }

    private @NotNull List<Role> getUserRoleAsList(){
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByName("USER").orElse(null);
        if(role == null){
            Role newRole = roleRepository.save(new Role("USER"));
            roles.add(newRole);
            return roles;
        }
        roles.add(role);
        return roles;
    }

    private Role findRoleByName(String roleName){
        return roleRepository.findByName(roleName).orElseThrow( () -> new ResourceNotFoundException("role '"+roleName+"' not found."));
    }

    private PageModel<UserIamResponse> fromUserIamPage(@NotNull Page<UserIam> iamPage, int page){
        return PageModel.<UserIamResponse>builder()
                .page(page)
                .totalElements(iamPage.getTotalElements())
                .totalPages(iamPage.getTotalPages())
                .numberOfElements(iamPage.getNumberOfElements())
                .number(iamPage.getNumber())
                .size(iamPage.getSize())
                .content(Mappers.fromListOfUserIAM(iamPage.getContent()))
                .hasContent(iamPage.hasContent())
                .hasNext(iamPage.hasNext())
                .hasPrevious(iamPage.hasPrevious())
                .isFirst(iamPage.isFirst())
                .isLast(iamPage.isLast())
                .build();
    }
}
