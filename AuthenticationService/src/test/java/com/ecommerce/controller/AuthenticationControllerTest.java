package com.ecommerce.controller;

import com.ecommerce.dto.request.LoginRequestDto;
import com.ecommerce.dto.request.RegisterRequestDto;
import com.ecommerce.dto.response.LoginResponseDto;
import com.ecommerce.model.Auth;
import com.ecommerce.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;
    @Mock
    private AuthenticationService authenticationService;
    @Test
    void registerAuth() {
        RegisterRequestDto requestDto = RegisterRequestDto.builder()
                .mail("example@gmail.com")
                .password("password")
                .name("name")
                .surname("surname")
                .build();

        Auth auth = Auth.builder()
                .mail("example@gmail.com")
                .password("password")
                .build();

        when(authenticationService.register(any(RegisterRequestDto.class))).thenReturn(auth);
        ResponseEntity<Auth> response = authenticationController.registerAuth(requestDto);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(auth, response.getBody());
    }

    @Test
    void login() {
        LoginRequestDto requestDto = LoginRequestDto.builder()
                .mail("example@gmail.com")
                .password("password")
                .build();

        LoginResponseDto responseDto = LoginResponseDto.builder()
                .token("x123x")
                .build();

        when(authenticationService.login(any(LoginRequestDto.class))).thenReturn(responseDto);
        ResponseEntity<LoginResponseDto> response = authenticationController.login(requestDto);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(responseDto,response.getBody());

    }
}