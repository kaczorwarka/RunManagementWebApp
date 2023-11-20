package com.WCY20IJ1S1.RunManagement.Controller;

import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<String> handleDuplicateKeyException(MongoWriteException ex) {
        return new ResponseEntity<>("Duplicate key error " + ex.getMessage(), HttpStatus.CONFLICT);
    }
}
