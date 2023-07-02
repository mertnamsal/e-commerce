package com.ecommerce.exception;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum EErrorType {
    INTERNAL_ERROR(3000,"Unexpected error on server",INTERNAL_SERVER_ERROR),
    INVALID_TOKEN(4001,"Invalid token information",BAD_REQUEST),
    BAD_REQUEST_ERROR(1202,"You have entered an invalid parameter",BAD_REQUEST),
    AUTH_REGISTER_ERROR(2000,"Already registered",BAD_REQUEST),
    INVALID_DATA(2000,"Wrong username or password",BAD_REQUEST),
    TOKEN_NOT_FOUND(4000,"Token not found",BAD_REQUEST),
    EMPTY_TOKEN(4001,"Token Empty",BAD_REQUEST),

    ;

    private int code;
    private String message;
    private HttpStatus httpStatus;
}
