package com.mounanga.enterprise.users.service;

import com.mounanga.enterprise.users.dto.request.UserIamRequest;
import com.mounanga.enterprise.users.dto.request.UserRoleRequest;
import com.mounanga.enterprise.users.dto.response.UserIamResponse;
import com.mounanga.enterprise.users.dto.model.PageModel;

public interface UserIamService {

    UserIamResponse getUserIamById(String id);
    UserIamResponse getCurrentUserIam();
    PageModel<UserIamResponse> getUserIamByPage(int page, int pageSize);
    PageModel<UserIamResponse> searchUserIamByPage(String keyword, int page, int pageSize);
    UserIamResponse createUserIam(UserIamRequest request);
    UserIamResponse updateUserIam(UserIamRequest request);
    void deleteUserIamById(String id);
    void deleteAllUserIam();
    void addRoleToUserIam(UserRoleRequest request);
    void removeRoleFromUserIam(UserRoleRequest request);
}
