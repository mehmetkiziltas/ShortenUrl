package com.tapu.shorterurl.service;

import com.tapu.shorterurl.model.User;
import com.tapu.shorterurl.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void saveUser() {
        //Given
        User user = new User(
                1,
                "Mehmet",
                "P4ssword",
                null
        );
        //when
        userRepository.save(user);
        //then
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        final User userCaptor = userArgumentCaptor.getValue();
        assertThat(userCaptor).isEqualTo(user);

    }

    @Test
    void deleteUser() {
        String username = "Mehmet";
        underTest.deleteUser(username);
        verify(userRepository, times(1)).findByUsername(username);
    }
}