package com.udacity.controller;


import com.udacity.TestUtils;
import com.udacity.model.User;
import com.udacity.repository.CartRepository;
import com.udacity.repository.UserRepository;
import com.udacity.request.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.udacity.TestUtils.createUser;
import static com.udacity.TestUtils.injectObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private static final String USERNAME = "test";
    private static final String PASSWORD = "testPassword";
    private static final String HASH = "thisIsHashed";

    private UserController userController;
    private final UserRepository userRepo = mock(UserRepository.class);
    private final CartRepository cartRepo = mock(CartRepository.class);
    private final BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        injectObject(userController, "userRepository", userRepo);
        injectObject(userController, "cartRepository", cartRepo);
        injectObject(userController, "bCryptPasswordEncoder", encoder);
    }

    @Test
    public void createUserHappyPathTest() {
        when(encoder.encode(PASSWORD)).thenReturn(HASH);
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(USERNAME);
        request.setPassword(PASSWORD);
        request.setConfirmPassword(PASSWORD);

        final ResponseEntity<User> response = userController.createUser(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals(USERNAME, user.getUsername());
        assertEquals(HASH, user.getPassword());
    }

    @Test
    public void canFindByUsernameTest() {
        when(userRepo.findByUsername(USERNAME)).thenReturn(createUser());

        final ResponseEntity<User> response = userController.findByUsername(USERNAME);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User actualUser = response.getBody();
        assertNotNull(actualUser);
        assertEquals(USERNAME, actualUser.getUsername());
    }

    @Test
    public void canFindById() {
        User user = createUser();
        Optional<User> userOptional = Optional.of(createUser());
        when(userRepo.findById(1L)).thenReturn(userOptional);

        final ResponseEntity<User> response = userController.findById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User actualUser = response.getBody();
        assertNotNull(actualUser);
        assertEquals(user.getUsername(), actualUser.getUsername());
    }
}
