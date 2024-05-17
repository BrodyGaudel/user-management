package com.mounanga.enterprise.users.restcontroller;

import com.mounanga.enterprise.users.dto.request.UserRootRequest;
import com.mounanga.enterprise.users.dto.response.UserRootResponse;
import com.mounanga.enterprise.users.service.UserRootService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/root")
public class UserRootRestController {

    private final UserRootService userRootService;

    public UserRootRestController(UserRootService userRootService) {
        this.userRootService = userRootService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get")
    public UserRootResponse getUser(){
        return userRootService.getUserRoot();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update")
    public UserRootResponse updateUserRoot(@RequestBody UserRootRequest request){
        return userRootService.updateUserRoot(request);
    }
}
