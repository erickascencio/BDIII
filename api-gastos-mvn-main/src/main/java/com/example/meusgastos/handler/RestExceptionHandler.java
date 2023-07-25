package com.example.meusgastos.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.meusgastos.common.ConversorData;
import com.example.meusgastos.domain.exception.ResourceBadRequestExcpetion;
import com.example.meusgastos.domain.exception.ResourceNotFoundExcpetion;
import com.example.meusgastos.domain.model.ErrorResposta;

@ControllerAdvice
public class RestExceptionHandler {
  
    @ExceptionHandler(ResourceNotFoundExcpetion.class)
    public ResponseEntity<ErrorResposta> handlerResourceNotFoundExecption(ResourceNotFoundExcpetion ex){
        String dataHora = ConversorData.converterDateParaDataHora(new Date());
        ErrorResposta erro =  new ErrorResposta(dataHora, 
        HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage());
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceBadRequestExcpetion.class)
    public ResponseEntity<ErrorResposta> handlerBadRequestResourceNotFoundExecption(ResourceBadRequestExcpetion ex){
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
