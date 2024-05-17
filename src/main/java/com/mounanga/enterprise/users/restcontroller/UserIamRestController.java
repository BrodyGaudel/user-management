package com.mounanga.enterprise.users.restcontroller;

import com.mounanga.enterprise.users.dto.model.PageModel;
import com.mounanga.enterprise.users.dto.request.UserIamRequest;
import com.mounanga.enterprise.users.dto.request.UserRoleRequest;
import com.mounanga.enterprise.users.dto.response.UserIamResponse;
import com.mounanga.enterprise.users.service.UserIamService;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/iam")
public class UserIamRestController {

    private final UserIamService userIamService;

    public UserIamRestController(UserIamService userIamService) {
        this.userIamService = userIamService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping("/get/{id}")
    public UserIamResponse getUserIamById(@PathVariable String id){
        return userIamService.getUserIamById(id);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping("/all")
    public PageModel<UserIamResponse> getUserIamByPage(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "pageSize", defaultValue = "10")  int pageSize){
        return userIamService.getUserIamByPage(page, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping("/search")
    public PageModel<UserIamResponse> searchUserIamByPage(@RequestParam(name = "keyword", defaultValue = " ") String keyword,
                                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "pageSize", defaultValue = "10")  int pageSize){
        return userIamService.searchUserIamByPage(keyword, page, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @PostMapping("/create")
    public UserIamResponse createUserIam(@RequestBody UserIamRequest request){
        return userIamService.createUserIam(request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @PutMapping("/update")
    public UserIamResponse updateUserIam(@RequestBody UserIamRequest request){
        return userIamService.updateUserIam(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteUserIamById(@PathVariable String id){
        userIamService.deleteUserIamById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/clear")
    public void deleteAllUserIam(){
        userIamService.deleteAllUserIam();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @PutMapping("/add-role")
    public void addRoleToUserIam(@RequestBody UserRoleRequest request){
        userIamService.addRoleToUserIam(request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @PutMapping("/remove-role")
    public void removeRoleFromUserIam(@RequestBody UserRoleRequest request){
        userIamService.removeRoleFromUserIam(request);
    }
}
