package com.ecommerce.service;

import com.ecommerce.controller.AuthenticationController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {

    @InjectMocks
    AuthenticationService authenticationService;
    @Mock
    AuthenticationController authenticationController;
    @Test
    void register() {
    }

    @Test
    void login() {
    }
}