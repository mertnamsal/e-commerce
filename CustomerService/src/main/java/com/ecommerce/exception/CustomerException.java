package com.ecommerce.exception;
import lombok.Getter;

@Getter
public class CustomerException extends RuntimeException{
    private final EErrorType EErrorType;

    public CustomerException(EErrorType EErrorType){
        super(EErrorType.getMessage());
        this.EErrorType = EErrorType;
    }


    public CustomerException(EErrorType EErrorType, String message){
        super(message);
        this.EErrorType = EErrorType;
    }


}