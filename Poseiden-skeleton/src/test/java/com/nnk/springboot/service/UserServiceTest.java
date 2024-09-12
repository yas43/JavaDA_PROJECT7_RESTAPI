package com.nnk.springboot.service;


import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.dto.*;
import com.nnk.springboot.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1, "testUser", "Test User", "password", "USER");
        userDTO = new UserDTO(1, "testUser", "Test User", "password123", "USER");
    }

    @Test
    @WithMockUser
    void testDisplayAllUser() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDTO> users = userService.displayAllUser();

        assertNotNull(users);
        assertEquals(1, users.size());
//        assertEquals("testUser", users.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @WithMockUser
    void testAddUser() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User addedUser = userService.addUser(userDTO);

        assertNotNull(addedUser);
//        assertEquals("encodedPassword", addedUser.getPassword());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }
//
    @Test
    @WithMockUser
    void testDisplayUserById_UserExists() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        UserDTO foundUserDTO = userService.displayUserById(1);

        assertNotNull(foundUserDTO);
//        assertEquals(1, foundUserDTO.getId());
//        assertEquals("testUser", foundUserDTO.getUsername());
        verify(userRepository, times(1)).findById(anyInt());
    }
//
    @Test
    @WithMockUser
    void testDisplayUserById_UserNotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.displayUserById(1);
        });

        assertEquals("could not find user by this id", exception.getMessage());
        verify(userRepository, times(1)).findById(anyInt());
    }
//
    @Test
    @WithMockUser
    void testUpdateUser_UserExists() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUser(1, userDTO);

        assertNotNull(updatedUser);
        assertEquals("encodedPassword", updatedUser.getPassword());
        verify(userRepository, times(1)).findById(anyInt());
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(anyString());
    }
//
    @Test
    @WithMockUser
    void testUpdateUser_UserNotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(1, userDTO);
        });

        assertEquals("could not find user  by given id", exception.getMessage());
        verify(userRepository, times(1)).findById(anyInt());
        verify(userRepository, times(0)).save(any(User.class));
    }
//
    @Test
    @WithMockUser
    void testDeleteUser_UserExists() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(any(User.class));

        userService.deleteUser(1);

        verify(userRepository, times(1)).findById(anyInt());
        verify(userRepository, times(1)).delete(any(User.class));
    }

    @Test
    @WithMockUser
    void testDeleteUser_UserNotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(1);
        });

        assertEquals("could not find user by this id", exception.getMessage());
        verify(userRepository, times(1)).findById(anyInt());
        verify(userRepository, times(0)).delete(any(User.class));
    }
}

