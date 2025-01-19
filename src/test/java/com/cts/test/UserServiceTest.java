package com.cts.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.exceptions.UserAlreadyExistException;
import com.cts.exceptions.UserNameNotFoundException;
import com.cts.model.User;
import com.cts.repo.UserRepo;
import com.cts.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setName("Test User");
        user.setPassword("password");
        user.setRoles("USER");
    }

    @Test
    public void testAddUser_UserAlreadyExists() {
        when(userRepo.existsByEmail(user.getEmail())).thenReturn(true);
        assertThrows(UserAlreadyExistException.class, () -> {
            userService.addUser(user);
        });
        verify(userRepo, never()).save(user);
    }

    @Test
    public void testAddUser_Success() {
        when(userRepo.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepo.save(user)).thenReturn(user);
        User savedUser = userService.addUser(user);
        assertEquals(user, savedUser);
        verify(userRepo, times(1)).save(user);
    }

    @Test
    public void testFindUserById_UserExists() {
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        Optional<User> foundUser = userService.findUserById(user.getId());
        assertEquals(Optional.of(user), foundUser);
    }

    @Test
    public void testFindUserById_UserDoesNotExist() {
        when(userRepo.findById(user.getId())).thenReturn(Optional.empty());
        assertThrows(UserNameNotFoundException.class, () -> {
            userService.findUserById(user.getId()).orElseThrow(() -> new UserNameNotFoundException("User not found with ID: " + user.getId()));
        });
    }
}