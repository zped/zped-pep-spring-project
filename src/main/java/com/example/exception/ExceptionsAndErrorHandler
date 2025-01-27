package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsAndErrorHandler {

    @ExceptionHandler(ClientErrorException.class)
    public @ResponseBody ResponseEntity<String> handleBadRequests(ClientErrorException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unacceptable input. Password must be between 4 and 255 characters, inclusive. " +
                "Username must be between 1 and 255 characters in length.");
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public @ResponseBody ResponseEntity<String> handleBadRequests(UnauthorizedAccessException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("We could not locate an account with that username and password.");
    }

    @ExceptionHandler(UsernameCollisionException.class)
    public @ResponseBody ResponseEntity<String> handleBadRequests(UsernameCollisionException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken.");
    }
}
