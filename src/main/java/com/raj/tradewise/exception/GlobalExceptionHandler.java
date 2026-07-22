package com.raj.tradewise.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String handleEmailAlreadyExistException(EmailAlreadyExistsException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public String handleInvalidCredentialsException(InvalidCredentialsException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(PortfolioNotFoundException.class)
    public String handlePortfolioNotFoundException(PortfolioNotFoundException ex){
        return ex.getMessage();
    }
}
