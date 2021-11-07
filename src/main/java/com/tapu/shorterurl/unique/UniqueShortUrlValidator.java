package com.tapu.shorterurl.unique;

import com.tapu.shorterurl.model.Url;
import com.tapu.shorterurl.repository.UrlRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueShortUrlValidator implements ConstraintValidator<UniqueShortUrl, String> {

    private final UrlRepository urlRepository;

    @Override
    public boolean isValid(final String shortUrl, final ConstraintValidatorContext context) {
        if (urlRepository != null) {
            Url url = urlRepository.findByAfterUrl(shortUrl);
            if (url != null) {
                return false;
            }
        }
        return true;
    }
}
