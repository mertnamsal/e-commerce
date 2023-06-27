package com.ecommerce.service;

import com.ecommerce.dto.request.RegisterRequestDto;
import com.ecommerce.exception.AuthenticationException;
import com.ecommerce.exception.EErrorType;
import com.ecommerce.model.Auth;
import com.ecommerce.repository.IAuthenticationRepository;
import com.ecommerce.utility.ServiceManagerImpl;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService extends ServiceManagerImpl<Auth,Long> {

    private final IAuthenticationRepository authenticationRepository;
    public AuthenticationService(IAuthenticationRepository authenticationRepository) {
        super(authenticationRepository);
        this.authenticationRepository = authenticationRepository;
    }

    public void register(RegisterRequestDto dto) {
        if(authenticationRepository.findOptionalByMail(dto.getMail()).isEmpty()){
            throw new AuthenticationException(EErrorType.AUTH_LOGIN_ERROR);
        }
        Auth auth = save(Auth.builder()
                .mail(dto.getMail())
                .password(dto.getPassword())
                .build()
                );

    }
}
