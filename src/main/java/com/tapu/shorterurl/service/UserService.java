package com.tapu.shorterurl.service;

import com.tapu.shorterurl.error.NotFoundException;
import com.tapu.shorterurl.model.User;
import com.tapu.shorterurl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(final User user) {
        String encryptedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    public void deleteUser(final String username) {
        User inDB = userRepository.findByUsername(username);
        userRepository.delete(inDB);
    }
}
