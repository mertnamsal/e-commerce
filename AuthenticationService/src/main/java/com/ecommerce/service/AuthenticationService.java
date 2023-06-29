package com.ecommerce.service;

import com.ecommerce.dto.request.LoginRequestDto;
import com.ecommerce.dto.request.RegisterRequestDto;
import com.ecommerce.dto.response.LoginResponseDto;
import com.ecommerce.exception.AuthenticationException;
import com.ecommerce.exception.EErrorType;
import com.ecommerce.model.Auth;
import com.ecommerce.rabbitmq.model.CreateUser;
import com.ecommerce.rabbitmq.producer.CreateUserProducer;
import com.ecommerce.repository.IAuthenticationRepository;
import com.ecommerce.utility.JwtTokenManager;
import com.ecommerce.utility.PasswordEncrypt;
import com.ecommerce.utility.ServiceManagerImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AuthenticationService extends ServiceManagerImpl<Auth, Long> {

    private final IAuthenticationRepository authenticationRepository;
    private final JwtTokenManager jwtTokenManager;
    private final PasswordEncrypt passwordEncrypt;
    private final CreateUserProducer createUserProducer;

    public AuthenticationService(IAuthenticationRepository authenticationRepository, JwtTokenManager jwtTokenManager, PasswordEncrypt passwordEncrypt, CreateUserProducer createUserProducer) {
        super(authenticationRepository);
        this.authenticationRepository = authenticationRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.passwordEncrypt = passwordEncrypt;
        this.createUserProducer = createUserProducer;
    }

    @Transactional
    public Auth register(RegisterRequestDto dto) {
        validateUniqueEmail(dto.getMail());
        String encryptedPassword = encryptPassword(dto.getPassword());
        Auth auth = save(Auth.builder()
                .mail(dto.getMail())
                .password(encryptedPassword)
                .build());

        sendMessageToCreateUserQueue(auth, dto);
        return auth;
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        String encryptedPassword = encryptPassword(dto.getPassword());
        Optional<Auth> auth = authenticationRepository.findOptionalByMailAndPassword(dto.getMail(), encryptedPassword);
        if (auth.isEmpty()) {
            throw new AuthenticationException(EErrorType.INVALID_DATA);
        }
        String token = generateAuthToken(auth.get().getId());
        return LoginResponseDto.builder().token(token).build();
    }

    private void validateUniqueEmail(String email) {
        if (authenticationRepository.findOptionalByMail(email).isPresent()) {
            throw new AuthenticationException(EErrorType.AUTH_REGISTER_ERROR);
        }
    }

    private String generateAuthToken(Long userId) {
        Optional<String> token = jwtTokenManager.generateToken(userId);
        if (token.isEmpty()) {
            throw new AuthenticationException(EErrorType.TOKEN_NOT_FOUND);
        }
        return token.get();
    }

    private String encryptPassword(String password) {
        return passwordEncrypt.generateEncryptPassword(password);
    }

    private void sendMessageToCreateUserQueue(Auth auth, RegisterRequestDto dto) {
        CreateUser createUser = CreateUser.builder()
                .authid(auth.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .build();
        createUserProducer.createSendMessage(createUser);
    }
}
