package com.ecommerce.service;

import com.ecommerce.dto.request.LoginRequestDto;
import com.ecommerce.dto.request.RegisterRequestDto;
import com.ecommerce.dto.response.LoginResponseDto;
import com.ecommerce.exception.AuthenticationException;
import com.ecommerce.exception.EErrorType;
import com.ecommerce.model.Auth;
import com.ecommerce.model.enums.ERole;
import com.ecommerce.rabbitmq.model.CreateCustomer;
import com.ecommerce.rabbitmq.producer.CreateCustomerProducer;
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
    private final CreateCustomerProducer createCustomerProducer;

    public AuthenticationService(IAuthenticationRepository authenticationRepository, JwtTokenManager jwtTokenManager, PasswordEncrypt passwordEncrypt, CreateCustomerProducer createCustomerProducer) {
        super(authenticationRepository);
        this.authenticationRepository = authenticationRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.passwordEncrypt = passwordEncrypt;
        this.createCustomerProducer = createCustomerProducer;
    }

    @Transactional
    public Auth register(RegisterRequestDto dto) {
        validateUniqueEmail(dto.getMail());
        String encryptedPassword = encryptPassword(dto.getPassword());
        Auth auth = save(Auth.builder()
                .mail(dto.getMail())
                .password(encryptedPassword)
                .role(ERole.CUSTOMER)
                .build());

        sendMessageToCreateCustomerQueue(auth, dto);
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

    private String generateAuthToken(Long customerId) {
        Optional<String> token = jwtTokenManager.generateToken(customerId);
        if (token.isEmpty()) {
            throw new AuthenticationException(EErrorType.TOKEN_NOT_FOUND);
        }
        return token.get();
    }

    private String encryptPassword(String password) {
        return passwordEncrypt.generateEncryptPassword(password);
    }

    private void sendMessageToCreateCustomerQueue(Auth auth, RegisterRequestDto dto) {
        CreateCustomer createCustomer = CreateCustomer.builder()
                .authid(auth.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .build();
        createCustomerProducer.createSendMessage(createCustomer);
    }

    public Boolean assignVendorRole(Long authId) {
        Auth auth = findById(authId);
        auth.setRole(ERole.VENDOR);
        save(auth);
        return true;
    }

    public Auth findByEmail(String mail) {
       Optional<Auth> auth = authenticationRepository.findOptionalByMail(mail);
       return auth.get();
    }
}
