package com.ecommerce.exception;
import lombok.Getter;

@Getter
public class ProductException extends RuntimeException{
    private final EErrorType EErrorType;

    public ProductException(EErrorType EErrorType){
        super(EErrorType.getMessage());
        this.EErrorType = EErrorType;
    }


    public ProductException(EErrorType EErrorType, String message){
        super(message);
        this.EErrorType = EErrorType;
    }


}