package com.example.apicustom.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.apicustom.commom.ConversorData;
import com.example.apicustom.domain.exception.ResourceBadRequestException;
import com.example.apicustom.domain.exception.ResourceNotFoundException;
import com.example.apicustom.domain.model.ErrorResposta;


@ControllerAdvice
public class RestExceptionHandler {
  
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResposta> handlerResourceNotFoundExecption(ResourceNotFoundException ex){
        String dataHora = ConversorData.converterDateParaDataHora(new Date());
        ErrorResposta erro =  new ErrorResposta(dataHora, 
        HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage());
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceBadRequestException.class)
    public ResponseEntity<ErrorResposta> handlerBadRequestResourceNotFoundExecption(ResourceBadRequestException ex){
        String dataHora = ConversorData.converterDateParaDataHora(new Date());
        ErrorResposta erro =  new ErrorResposta(dataHora, 
        HttpStatus.BAD_REQUEST.value(), "Bad Request", ex.getMessage());
        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResposta> handlerRequestExecption(Exception ex){
        String dataHora = ConversorData.converterDateParaDataHora(new Date());
        ErrorResposta erro =  new ErrorResposta(dataHora, 
        HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", ex.getMessage());
        return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
