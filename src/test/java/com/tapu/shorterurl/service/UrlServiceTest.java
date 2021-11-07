package com.tapu.shorterurl.service;

import com.tapu.shorterurl.error.NotFoundException;
import com.tapu.shorterurl.model.Url;
import com.tapu.shorterurl.model.User;
import com.tapu.shorterurl.repository.UrlRepository;
import com.tapu.shorterurl.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UserRepository userRepository;

    private UrlService urlServiceTest;

    @BeforeEach
    void setUp() {
        urlServiceTest = new UrlService(urlRepository, userRepository);
    }

    @Test
    void save() {
        //Given
        Url url = new Url(
                1,
                "http://localhost:8080/s/jwwVlkAlHe",
                "https://www.hepsiburada.com/laptop-notebook-dizustu-bilgisayarlar-c-98",
                new User(
                        1,
                        "Mehmet",
                        "P4ssword",
                        null
                )
        );
        //when
        urlRepository.save(url);
        //then
        ArgumentCaptor<Url> urlArgumentCaptor =
                ArgumentCaptor.forClass(Url.class);
        verify(urlRepository).save(urlArgumentCaptor.capture());
        final Url captorValue = urlArgumentCaptor.getValue();
        assertThat(captorValue).isEqualTo(url);
    }

/*    @Test
    void saveWillThrow() {
        //Given
        Url url = new Url(
                1,
                "http://localhost:8080/s/jwwVlkAlHe",
                "https://www.hepsiburada.com/laptop-notebook-dizustu-bilgisayarlar-c-98",
                new User(
                        1,
                        "Mehmet",
                        "P4ssword",
                        null
                )
        );
        given(urlRepository.findByAfterUrl(url.getAfterUrl()))
                .willReturn(url);
        //when
        //then
        assertThatThrownBy(() -> urlRepository.save(url))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Url is null");
        verify(urlRepository, never()).save(any());
    }*/

    @Test
    void delete() {
        long userId = 1;
        long urlId = 1;
        urlServiceTest.delete(userId, urlId);
        verify(urlRepository, times(1)).getByUser_IdAndId(urlId, userId);
    }

    @Test
    void getUrl() {
        String shortUrl = "http://localhost:8080/s/jwwVlkAlHe";
        urlServiceTest.getUrl(shortUrl);
        verify(urlRepository).findByAfterUrl(shortUrl);
    }

    @Test
    void getUserUrls() {
        long userId = 1;
        urlServiceTest.getUserUrls(userId);
        verify(urlRepository).findByUser(userRepository.getById(userId));
    }

    @Test
    void getUserUrlsWithThrown() {
        final NotFoundException exception = assertThrows(
                NotFoundException.class, () ->
                    urlServiceTest.getUserUrls(isNull()),
                            "Expected getUserUrls() to throw, but it didn't"
                );
        assertTrue(exception.getMessage().contains("Not found"));
    }

    @Test
    void getUserUrl() {
        long userId = 1;
        long urlId = 1;
        urlServiceTest.getUserUrl(userId, urlId);
        verify(urlRepository).getByUser_IdAndId(userId, urlId);
    }

    @Test
    void getByShortUrl() {
        String shortUrl = "http://localhost:8080/s/jwwVlkAlHe";
        urlServiceTest.getByShortUrl(shortUrl);
        verify(urlRepository).getByAfterUrl(shortUrl);
    }
}