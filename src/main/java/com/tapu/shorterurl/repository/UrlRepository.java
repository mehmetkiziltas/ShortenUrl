package com.tapu.shorterurl.repository;

import com.tapu.shorterurl.model.Url;
import com.tapu.shorterurl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Long> {

    List<Url> findByUser(User user);
    Url findByAfterUrl(String afterUrl);
    Url getByUser_IdAndId(long userId, long urlId);
    Url getByAfterUrl(String s);
}
