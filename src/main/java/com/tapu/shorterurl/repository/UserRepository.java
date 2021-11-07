package com.tapu.shorterurl.repository;

import com.tapu.shorterurl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User getById(long id);
}
