package com.searchmetrics.exchange.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
@Log4j2
public class ExceptionAdviceController {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity handleInvalidEvalRequestException(IllegalArgumentException exception) {
        log.error(exception);
         return new ResponseEntity<>("{\"msg\" : \"Invalid Request\"}", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({ConversionFailedException.class})
    public ResponseEntity handleInvalidEvalRequestException(ConversionFailedException exception) {
        log.error(exception);
        return new ResponseEntity<>("{\"msg\" : \"Invalid Request\"}", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({IOException.class})
    public ResponseEntity handleInvalidEvalRequestException(IOException exception) {
        log.error(exception);
        return new ResponseEntity<>("{\"msg\" : \"Server Error\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
