package com.qima.tech.services;

import com.qima.tech.dtos.user.UserDTO;
import com.qima.tech.dtos.user.CreateUserDTO;
import com.qima.tech.dtos.user.UpdateUserDTO;
import com.qima.tech.entities.User;
import com.qima.tech.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "testuser", "test@example.com", "password", Set.of("ROLE_USER"));
    }

    @Test
    void shouldFindUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<UserDTO> result = userService.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo(user.getUsername());
        verify(userRepository).findById(1L);
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<UserDTO> result = userService.findById(99L);

        assertThat(result).isEmpty();
        verify(userRepository).findById(99L);
    }

    @Test
    void shouldGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDTO> users = userService.getAllUsers();

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getUsername()).isEqualTo(user.getUsername());
        verify(userRepository).findAll();
    }

    @Test
    void shouldCreateUser() {
        CreateUserDTO dto = new CreateUserDTO("newuser", "newuser@example.com", "123456");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            return new User(
                    savedUser.getId(),
                    savedUser.getUsername(),
                    savedUser.getEmail(),
                    savedUser.getPassword(),
                    savedUser.getRoles() != null ? savedUser.getRoles() : new HashSet<>()
            );
        });

        UserDTO createdUser = userService.createUser(dto);

        assertNotNull(createdUser);
        assertEquals("newuser", createdUser.getUsername());
        assertEquals("newuser@example.com", createdUser.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUsernameExists() {
        CreateUserDTO dto = new CreateUserDTO("testuser", "test@example.com", "123456");
        when(userRepository.existsByUsername(dto.getUsername())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.createUser(dto));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldUpdateUser() {
        UpdateUserDTO dto = new UpdateUserDTO("updated@example.com", "newpassword");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("newHashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO updatedUser = userService.updateUser(1L, dto);

        assertThat(updatedUser.getEmail()).isEqualTo(dto.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentUser() {
        UpdateUserDTO dto = new UpdateUserDTO("updated@example.com", "newpassword");
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateUser(99L, dto));

        verify(userRepository, never()).save(any(User.class));
    }
}
