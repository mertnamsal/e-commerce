package com.ecommerce.controller;

import com.ecommerce.dto.request.RegisterRequestDto;
import com.ecommerce.model.Auth;
import com.ecommerce.service.AuthenticationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<Auth> registerAuth(@RequestBody RegisterRequestDto dto){
        authenticationService.register(dto);
        return new ResponseEntity<>(Auth.builder().mail(dto.getMail()).password(dto.getPassword()).build(), HttpStatus.CREATED);
    }



}
