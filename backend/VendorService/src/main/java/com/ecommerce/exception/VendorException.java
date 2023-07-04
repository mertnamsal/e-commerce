package com.ecommerce.exception;
import lombok.Getter;

@Getter
public class VendorException extends RuntimeException{
    private final EErrorType EErrorType;

    public VendorException(EErrorType EErrorType){
        super(EErrorType.getMessage());
        this.EErrorType = EErrorType;
    }


    public VendorException(EErrorType EErrorType, String message){
        super(message);
        this.EErrorType = EErrorType;
    }


}