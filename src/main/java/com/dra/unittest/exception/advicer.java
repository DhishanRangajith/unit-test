package com.dra.unittest.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class advicer {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundExp(NotFoundException notFoundException){
        Map<String, String> data = new HashMap<>();
        data.put("message", notFoundException.getMessage());
        return new ResponseEntity<>(data, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
