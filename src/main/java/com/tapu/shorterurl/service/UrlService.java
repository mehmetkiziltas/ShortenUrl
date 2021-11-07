package com.tapu.shorterurl.service;

import com.tapu.shorterurl.error.NotFoundException;
import com.tapu.shorterurl.model.Url;
import com.tapu.shorterurl.model.User;
import com.tapu.shorterurl.repository.UrlRepository;
import com.tapu.shorterurl.repository.UserRepository;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final UserRepository userRepository;

    public void save(Url url, long id) {
        if (url != null) {
            final User user = userRepository.getById(id);
            url.setUser(user);
            urlRepository.save(url);
        }
    }

    public void delete(final long userId,
                       final long urlId) {
        final Url url = urlRepository.getByUser_IdAndId(userId, urlId);
        urlRepository.delete(url);
    }

    public Url getUrl(String url) {
        return urlRepository.findByAfterUrl(url);
    }

    public List<Url> getUserUrls(final long id) {
        try {
            User inDB = userRepository.getById(id);
            return urlRepository.findByUser(inDB);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }

    public Url getUserUrl(final long userId, final long urlId) {
        return urlRepository.getByUser_IdAndId(userId, urlId);
    }

    public Url getByShortUrl(final String s) {
        return urlRepository.getByAfterUrl(s);
    }
}
