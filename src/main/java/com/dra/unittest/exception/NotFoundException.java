package com.dra.unittest.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message){
        super(message);
    }
}
