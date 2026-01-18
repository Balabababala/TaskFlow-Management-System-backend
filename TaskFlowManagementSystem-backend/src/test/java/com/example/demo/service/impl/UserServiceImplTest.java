package com.example.demo.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    // ========= createUser =========

    @Test
    void createUser_success() {
        UserDto dto = mockUserDto();
        User entity = mockUser();

        when(userRepository.existsByUsername(dto.getUsername()))
                .thenReturn(false);
        when(userMapper.toEntity(dto))
                .thenReturn(entity);

        userService.createUser(dto);

        verify(userRepository).save(entity);
    }

    @Test
    void createUser_usernameExists_throwException() {
        UserDto dto = mockUserDto();

        when(userRepository.existsByUsername(dto.getUsername()))
                .thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(dto));

        verify(userRepository, never()).save(any());
    }

    // ========= updateUser =========

    @Test
    void updateUser_success() {
        UserDto dto = mockUserDto();
        dto.setId(1L);

        User existingUser = mockUser();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(existingUser));

        userService.updateUser(dto);

        verify(userMapper).updateEntity(dto, existingUser);
        verify(userRepository).save(existingUser);
    }

    @Test
    void updateUser_userNotFound_throwException() {
        UserDto dto = mockUserDto();
        dto.setId(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.updateUser(dto));
    }

    // ========= deleteUser =========

    @Test
    void deleteUser_success() {
        User user = mockUser();
        user.setActive(true);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        assertFalse(user.getActive());
        verify(userRepository).save(user);
    }

    @Test
    void deleteUser_alreadyDeleted_throwException() {
        User user = mockUser();
        user.setActive(false);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        assertThrows(IllegalStateException.class,
                () -> userService.deleteUser(1L));
    }

    // ========= restoreUser =========

    @Test
    void restoreUser_success() {
        User user = mockUser();
        user.setActive(false);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        userService.restoreUser(1L);

        assertTrue(user.getActive());
        verify(userRepository).save(user);
    }

    @Test
    void restoreUser_alreadyActive_throwException() {
        User user = mockUser();
        user.setActive(true);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        assertThrows(IllegalStateException.class,
                () -> userService.restoreUser(1L));
    }

    // ========= findUser =========

    @Test
    void findUser_success() {
        User user = mockUser();
        UserDto dto = mockUserDto();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        when(userMapper.toDto(user))
                .thenReturn(dto);

        UserDto result = userService.findUser(1L);

        assertNotNull(result);
        verify(userMapper).toDto(user);
    }

    @Test
    void findUser_notFound_throwException() {
        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.findUser(1L));
    }

    // ========= findAllUser =========

    @Test
    void findAllUser_success() {
        User user = mockUser();
        UserDto dto = mockUserDto();

        when(userRepository.findAll())
                .thenReturn(List.of(user));
        when(userMapper.toDto(user))
                .thenReturn(dto);

        List<UserDto> result = userService.findAllUser();

        assertEquals(1, result.size());
        verify(userMapper).toDto(user);
    }

    // ========= mock helpers =========

    private User mockUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setActive(true);
        return user;
    }

    private UserDto mockUserDto() {
        UserDto dto = new UserDto();
        dto.setUsername("test");
        dto.setPassword("password");
        dto.setEmail("test@test.com");
        dto.setFullName("Test User");
        return dto;
    }
}
