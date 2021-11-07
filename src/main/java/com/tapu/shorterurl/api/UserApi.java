package com.tapu.shorterurl.api;

import com.tapu.shorterurl.model.User;
import com.tapu.shorterurl.model.vm.UserVm;
import com.tapu.shorterurl.service.UserService;
import com.tapu.shorterurl.shared.GenericResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserApi {

    private final UserService userService;

    public UserApi(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public GenericResponse createUser(@Valid @RequestBody UserVm userVm) {
        User user = new User();
        user.setUsername(userVm.getUsername());
        user.setPassword(userVm.getPassword());
        userService.saveUser(user);
        return new GenericResponse("userId : " + user.getId());
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("#username == principal.username")
    GenericResponse deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new GenericResponse("User is removed");
    }
}
