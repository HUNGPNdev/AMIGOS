package com.amigos.errors;

import com.amigos.common.ResponseApi;
import com.amigos.errors.handle.ObjectDuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ObjectDuplicateException.class})
    protected ResponseEntity<Object> objectIsExisting (ObjectDuplicateException ex) {
        ResponseApi errorResponse = new ResponseApi(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getData());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
