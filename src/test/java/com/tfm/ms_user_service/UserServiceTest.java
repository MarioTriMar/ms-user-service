package com.tfm.ms_user_service;

import com.tfm.ms_user_service.model.User;
import com.tfm.ms_user_service.model.UserDTO;
import com.tfm.ms_user_service.model.UserOrder;
import com.tfm.ms_user_service.repository.UserRepository;
import com.tfm.ms_user_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void shouldReturnForbiddenWhenEmailAlreadyUsed() {
        UserDTO dto = new UserDTO();
        dto.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(new User()));

        ResponseEntity<?> response = userService.register(dto);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("email already used", response.getBody());
    }
    @Test
    void shouldRegisterUserSuccessfully() {
        UserDTO dto = new UserDTO();
        dto.setEmail("new@example.com");
        dto.setName("Test");
        dto.setPassword1("pass123");
        dto.setPhone("123456789");
        dto.setAddress("Street 123");

        when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());

        ResponseEntity<?> response = userService.register(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User created", response.getBody());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldReturnUserOrderWhenUserExists() {
        User user = new User();
        user.setId("1");
        user.setEmail("test@example.com");

        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        ResponseEntity<?> response = userService.getUser("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserOrder userOrder = (UserOrder) response.getBody();
        assertEquals("1", userOrder.getId());
        assertEquals("test@example.com", userOrder.getEmail());
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        ResponseEntity<?> response = userService.getUser("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not fount", response.getBody());
    }

}