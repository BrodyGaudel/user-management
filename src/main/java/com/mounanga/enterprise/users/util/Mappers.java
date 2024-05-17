package com.mounanga.enterprise.users.util;

import com.mounanga.enterprise.users.dto.request.UserIamRequest;
import com.mounanga.enterprise.users.dto.request.UserRootRequest;
import com.mounanga.enterprise.users.dto.request.WorkspaceRequest;
import com.mounanga.enterprise.users.dto.response.UserIamResponse;
import com.mounanga.enterprise.users.dto.response.UserRootResponse;
import com.mounanga.enterprise.users.dto.response.WorkspaceResponse;
import com.mounanga.enterprise.users.entity.UserIam;
import com.mounanga.enterprise.users.entity.UserRoot;
import com.mounanga.enterprise.users.entity.Workspace;

import java.util.List;

public class Mappers {

    private Mappers() {
        super();
    }

    public static UserRootResponse fromUserRoot(UserRoot root) {
        if(root == null){
            return null;
        }
        UserRootResponse response = new UserRootResponse();
        response.setId(root.getId());
        response.setFirstname(root.getFirstname());
        response.setLastname(root.getLastname());
        response.setEmail(root.getEmail());
        response.setUsername(root.getUsername());
        response.setEnabled(root.getEnabled());
        response.setDateOfBirth(root.getDateOfBirth());
        response.setGender(root.getGender());
        response.setPlaceOfBirth(root.getPlaceOfBirth());
        response.setLastLogin(root.getLastLogin());
        response.setCreatedBy(root.getCreatedBy());
        response.setCreatedDate(root.getCreatedDate());
        response.setLastModifiedBy(root.getLastModifiedBy());
        response.setLastModifiedDate(root.getLastModifiedDate());
        response.setWorkspace(fromWorkspace(root.getWorkspace()));
        return response;
    }

    public static UserIamResponse fromUserIAM(UserIam iam) {
        if(iam == null){
            return null;
        }
        UserIamResponse response = new UserIamResponse();
        response.setId(iam.getId());
        response.setFirstname(iam.getFirstname());
        response.setLastname(iam.getLastname());
        response.setUsername(iam.getUsername());
        response.setCreatedBy(iam.getCreatedBy());
        response.setCreatedDate(iam.getCreatedDate());
        response.setEnabled(iam.getEnabled());
        response.setLastLogin(iam.getLastLogin());
        response.setLastModifiedBy(iam.getLastModifiedBy());
        response.setLastModifiedDate(iam.getLastModifiedDate());
        response.setWorkspaceId(getWorkspaceId(iam.getWorkspace()));
        response.setWorkspaceName(getWorkspaceName(iam.getWorkspace()));
        return response;
    }

    public static List<UserIamResponse> fromListOfUserIAM(List<UserIam> iamList) {
        if(iamList == null || iamList.isEmpty()){
            return List.of();
        }
        return iamList.stream().map(Mappers::fromUserIAM).toList();
    }

    public static UserRoot fromUserRootRequest(UserRootRequest request) {
        if(request == null){
            return null;
        }
        UserRoot root = new UserRoot();
        root.setFirstname(request.getFirstname());
        root.setLastname(request.getLastname());
        root.setPlaceOfBirth(request.getPlaceOfBirth());
        root.setDateOfBirth(request.getDateOfBirth());
        root.setNationality(request.getNationality());
        root.setGender(request.getGender());
        root.setEmail(request.getEmail());
        root.setUsername(request.getUsername());
        root.setPassword(request.getPassword());
        root.setWorkspace(fromWorkspaceRequest(request.getWorkspace()));
        return root;
    }

    public static UserIam fromUserIamRequest(UserIamRequest request) {
        if(request == null){
            return null;
        }
        UserIam iam = new UserIam();
        iam.setId(request.getId());
        iam.setFirstname(request.getFirstname());
        iam.setLastname(request.getLastname());
        iam.setUsername(request.getUsername());
        iam.setPassword(request.getPassword());
        iam.setStatus(request.getStatus());
        return iam;
    }

    private static WorkspaceResponse fromWorkspace(Workspace workspace) {
        if(workspace == null){
            return null;
        }
        return new WorkspaceResponse(
                workspace.getId(),
                workspace.getName(),
                workspace.getDescription()
        );
    }

    private static String getWorkspaceId(Workspace workspace) {
        if(workspace == null){
            return null;
        }
        return workspace.getId();
    }

    private static String getWorkspaceName(Workspace workspace) {
        if(workspace == null){
            return null;
        }
        return workspace.getName();
    }

    public static Workspace fromWorkspaceRequest(WorkspaceRequest request) {
        if(request == null){
            return null;
        }
        Workspace workspace = new Workspace();
        workspace.setDescription(request.getDescription());
        workspace.setName(request.getName());
        return workspace;
    }
}
