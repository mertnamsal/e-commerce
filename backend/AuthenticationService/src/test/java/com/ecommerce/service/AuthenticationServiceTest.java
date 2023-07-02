package com.ecommerce.service;

import com.ecommerce.dto.request.LoginRequestDto;
import com.ecommerce.dto.request.RegisterRequestDto;
import com.ecommerce.dto.response.LoginResponseDto;
import com.ecommerce.exception.AuthenticationException;
import com.ecommerce.model.Auth;
import com.ecommerce.rabbitmq.model.CreateCustomer;
import com.ecommerce.rabbitmq.producer.CreateCustomerProducer;
import com.ecommerce.repository.IAuthenticationRepository;
import com.ecommerce.utility.JwtTokenManager;
import com.ecommerce.utility.PasswordEncrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private IAuthenticationRepository authenticationRepository;
    @Mock
    private JwtTokenManager jwtTokenManager;
    @Mock
    private PasswordEncrypt passwordEncrypt;
    @Mock
    private CreateCustomerProducer createCustomerProducer;
    @InjectMocks
    private AuthenticationService authenticationService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testRegister() {
        RegisterRequestDto requestDto = RegisterRequestDto.builder()
                .mail("example@gmail.com")
                .password("password")
                .name("name")
                .surname("surname")
                .build();

        Auth savedAuth = Auth.builder()
                .mail("example@gmail.com")
                .password("password")
                .build();

        when(authenticationRepository.save(any(Auth.class))).thenReturn(savedAuth);

        Auth registeredAuth = authenticationService.register(requestDto);

        assertEquals(savedAuth, registeredAuth);
    }
    @Test
    public void testRegister_ThrowsAuthenticationException_WhenEmailIsNotUnique() {
        RegisterRequestDto requestDto = RegisterRequestDto.builder()
                .mail("example@gmail.com")
                .password("password")
                .name("name")
                .surname("surname")
                .build();

        Auth existingAuth = new Auth();

        when(authenticationRepository.findOptionalByMail(anyString())).thenReturn(Optional.of(existingAuth));

        assertThrows(AuthenticationException.class, () -> authenticationService.register(requestDto));
        verify(authenticationRepository, never()).save(any(Auth.class));
        verify(createCustomerProducer, never()).createSendMessage(any(CreateCustomer.class));
    }
    @Test
    public void testLogin(){
        LoginRequestDto requestDto = LoginRequestDto.builder()
                .mail("example@gmail.com")
                .password("password")
                .build();

        String encryptedPassword = "password";

        Auth auth = Auth.builder()
                .id(1L)
                .mail("example@gmail.com")
                .password("password")
                .build();

        when(passwordEncrypt.generateEncryptPassword(anyString())).thenReturn(encryptedPassword);
        when(authenticationRepository.findOptionalByMailAndPassword(anyString(), anyString())).thenReturn(Optional.of(auth));
        when(jwtTokenManager.generateToken(anyLong())).thenReturn(Optional.of("token"));

        LoginResponseDto response = authenticationService.login(requestDto);

        assertNotNull(response);
        assertEquals("token",response.getToken());
    }
    @Test
    public void testLogin_ThrowsAuthenticationException_WhenInvalidCredentials() {
        LoginRequestDto requestDto = LoginRequestDto.builder()
                .mail("example@gmail.com")
                .password("password")
                .build();

        String encryptedPassword = "password";

        when(passwordEncrypt.generateEncryptPassword(anyString())).thenReturn(encryptedPassword);
        when(authenticationRepository.findOptionalByMailAndPassword(anyString(), anyString())).thenReturn(Optional.empty());

        assertThrows(AuthenticationException.class, () -> authenticationService.login(requestDto));
    }




}
