package com.zhuk.beautyshop.exception.handler;

import com.zhuk.beautyshop.exception.exceptions.AppointmentNotFoundException;
import com.zhuk.beautyshop.exception.exceptions.FavourNotFoundException;
import com.zhuk.beautyshop.exception.exceptions.MasterNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedException() {
        log.error("Access denied exception");
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ExceptionResponse> appointmentNotFoundException(AppointmentNotFoundException ex) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());
        log.info(ex.getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(FavourNotFoundException.class)
    public ResponseEntity<ExceptionResponse> appointmentNotFoundException(FavourNotFoundException ex) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());
        log.info(ex.getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(MasterNotFoundException.class)
    public ResponseEntity<ExceptionResponse> appointmentNotFoundException(MasterNotFoundException ex) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());
        log.info(ex.getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

}
