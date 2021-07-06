package ru.demmax93.sweater.service;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.demmax93.sweater.domain.User;
import ru.demmax93.sweater.domain.enums.Role;
import ru.demmax93.sweater.repository.UserRepository;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MailSenderService mailSender;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void checkUserExistsPositiveTest() {
        Assertions.assertFalse(userService.checkUserExists(new User()));
    }

    @Test
    void checkUserExistsNegativeTest() {
        User user = new User();
        user.setUsername("John");
        Mockito.doReturn(new User())
                .when(userRepository)
                .findByUsername("John");
        boolean isUserFound = userService.checkUserExists(user);
        Assertions.assertTrue(isUserFound);
    }


    @Test
    void addUserPositiveTest() {
        String password = "1234";
        User user = new User();
        user.setEmail("some@mail.ru");
        user.setPassword(password);
        Mockito.doReturn("newPassword").when(passwordEncoder).encode(password);
        userService.addUser(user);
        Assertions.assertFalse(user.isActive());
        Assertions.assertNotNull(user.getActivationCode());
        Assertions.assertNotNull(user.getPassword());
        Assertions.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(password);
        Mockito.verify(mailSender,
                Mockito.times(1))
                .send(ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
    }

    @Test
    void activatedUserPositiveTest() {
        User user = new User();
        user.setActivationCode("activate");
        Mockito.doReturn(user)
                .when(userRepository)
                .findByActivationCode("activate");
        boolean isUserActivated = userService.activatedUser("activate");
        Assertions.assertTrue(isUserActivated);
        Assertions.assertTrue(user.isActive());
        Assertions.assertNull(user.getActivationCode());
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void activatedUserNegativeTest() {
        boolean isUserActivated = userService.activatedUser("activate me");
        Assertions.assertFalse(isUserActivated);
        Mockito.verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }
}