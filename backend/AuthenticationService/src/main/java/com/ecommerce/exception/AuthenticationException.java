package com.ecommerce.exception;
import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException{
    private final EErrorType EErrorType;

    public AuthenticationException(EErrorType EErrorType){
        super(EErrorType.getMessage());
        this.EErrorType = EErrorType;
    }


    public AuthenticationException(EErrorType EErrorType, String message){
        super(message);
        this.EErrorType = EErrorType;
    }


}