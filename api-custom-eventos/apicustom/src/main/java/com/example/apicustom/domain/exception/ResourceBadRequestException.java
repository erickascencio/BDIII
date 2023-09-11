package com.example.apicustom.domain.exception;

public class ResourceBadRequestException extends RuntimeException {

    public ResourceBadRequestException(String message){
        super(message);
    }
}
