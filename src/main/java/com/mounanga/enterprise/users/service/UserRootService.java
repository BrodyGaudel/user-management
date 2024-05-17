package com.mounanga.enterprise.users.service;

import com.mounanga.enterprise.users.dto.request.UserRootRequest;
import com.mounanga.enterprise.users.dto.response.UserRootResponse;

public interface UserRootService {

    UserRootResponse getUserRoot();
    UserRootResponse updateUserRoot(UserRootRequest request);
    void deleteUserRoot();
}
