package com.tapu.shorterurl.model.vm;

import com.tapu.shorterurl.model.User;
import lombok.Data;

@Data
public class UserVm {
    private String username;
    private String password;

    public UserVm(User user) {
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
    }

}
