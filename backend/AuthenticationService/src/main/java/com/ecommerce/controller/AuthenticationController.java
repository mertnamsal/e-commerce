package com.ecommerce.controller;

import com.ecommerce.dto.request.LoginRequestDto;
import com.ecommerce.dto.request.RegisterRequestDto;
import com.ecommerce.dto.response.LoginResponseDto;
import com.ecommerce.model.Auth;
import com.ecommerce.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register with mail,password,name and surname")
    public ResponseEntity<Auth> registerAuth(@RequestBody @Valid RegisterRequestDto dto){
        return new ResponseEntity<>(authenticationService.register(dto), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    @Operation(summary = "Login with mail and password")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto){
        return ResponseEntity.ok(authenticationService.login(dto));
    }
    @PutMapping("/assign-vendor/{authid}")
    public ResponseEntity<Boolean> assignVendorRole(@PathVariable Long authid){
        return ResponseEntity.ok(authenticationService.assignVendorRole(authid));

    }




}
