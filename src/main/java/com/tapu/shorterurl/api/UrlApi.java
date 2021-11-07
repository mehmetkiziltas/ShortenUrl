package com.tapu.shorterurl.api;

import com.tapu.shorterurl.model.Url;
import com.tapu.shorterurl.model.vm.UrlVm;
import com.tapu.shorterurl.service.UrlService;
import com.tapu.shorterurl.shared.GenericResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UrlApi {

    private final UrlService urlService;
    private final HttpServletResponse response;

    //POST http://localhost:8080/user/1/url/create
    @PostMapping("/user/{id}/url/create")
    public GenericResponse saveUrl(@Valid @RequestBody UrlVm urlVm,
                                   @PathVariable("id") long id) {
        Url url = new Url();
        getShortenUrl(urlVm);
        url.setBeforeUrl(urlVm.getBeforeUrlVm());
        url.setAfterUrl(getNewUrlFormat());
        urlService.save(url, id);
        return new GenericResponse(url.getAfterUrl());
    }

    //GET http://localhost:8080/user/{userId}/url/list
    @GetMapping("/user/{id}/url/list")
    public List<Url> getAllUserUrl(@PathVariable("id") final long id) {
        System.out.println(urlService.getUserUrls(id));
        return urlService.getUserUrls(id);
    }

    //GET http://localhost:8080/user/{userId}/url/detail/{urlId}
    @GetMapping("/user/{userId}/url/detail/{urlId}")
    public Url getUserUrl(@PathVariable("userId") final long userId,
                          @PathVariable() final long urlId) {
        return urlService.getUserUrl(userId, urlId);
    }

    //DELETE http://localhost:8080/user/{userId}/url/detail/{urlId}
    @DeleteMapping("/user/{userId}/url/detail/{urlId}")
    public GenericResponse delete(@PathVariable("userId") long userId,
                                  @PathVariable("urlId") long urlId) {
        urlService.delete(userId, urlId);
        return new GenericResponse("Url removed");
    }

    @GetMapping(value = "/s/{randomstring}")
    public void getFullUrl(@PathVariable("randomstring") String randomstring) throws IOException {
        Url inDB = urlService.getUrl("http://localhost:8080/s/" + randomstring);
        if (inDB != null) {
            response.sendRedirect(inDB.getBeforeUrl());
        } else {
            response.sendRedirect(shortenUrlMap.get(randomstring).getBeforeUrlVm());
        }
    }

    private final Map<String, UrlVm> shortenUrlMap = new HashMap<>();

    public void getShortenUrl(UrlVm urlVm) {
        String randomChar = getRandomChars();
        setShortenUrl(urlVm, randomChar);
    }

    private void setShortenUrl(UrlVm urlVm,
                               String randomChar) {
        Url url = new Url();
        Url inDB = urlService.getByShortUrl("http://localhost:8080/s/" + randomChar);
        if (inDB != null) {
            getShortenUrl(urlVm);
        }
        url.setAfterUrl("http://localhost:8080/s/" + randomChar);
        shortenUrlMap.put(randomChar, urlVm);
        setNewUrlFormat("http://localhost:8080/s/" + randomChar);
    }

    public String getRandomChars() {
        String randomStr = "";
        String possibleChars = "QAZWSXCDERFVBGTYHNMJUIKLOP" + "qazwsxedcrfvtgbyhnujmikolp";
        for (int i = 0; i < 10; i++) {
            //noinspection StringConcatenationInLoop
            randomStr += possibleChars.charAt((int) Math.floor(Math.random() * possibleChars.length()));
        }
        return randomStr;
    }

    @Getter
    @Setter
    private String newUrlFormat;
}
