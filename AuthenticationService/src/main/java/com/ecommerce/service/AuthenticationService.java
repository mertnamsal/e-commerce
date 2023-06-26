package com.ecommerce.service;

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
}
