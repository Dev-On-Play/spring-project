package mos.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    Logger defaultLog = LoggerFactory.getLogger(ExceptionAdvice.class);
    Logger exceptionLog = LoggerFactory.getLogger("ExceptionLogger");

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(Exception e) {
        defaultLog.error(e.getMessage());
        exceptionLog.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}
