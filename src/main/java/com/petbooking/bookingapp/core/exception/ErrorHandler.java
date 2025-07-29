package com.petbooking.bookingapp.core.exception;

import com.petbooking.bookingapp.dto.ResponseMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class ErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(AppGenericException.class)
    public ResponseEntity<ResponseMessageDTO> handleAppGenericException(AppGenericException ex) {
        ResponseMessageDTO response = new ResponseMessageDTO(
                LocalDateTime.now(),
                ex.getCode(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AppObjectNotFoundException.class)
    public ResponseEntity<ResponseMessageDTO> handleNotFound(AppObjectNotFoundException ex) {
        ResponseMessageDTO response = new ResponseMessageDTO(
                LocalDateTime.now(),
                ex.getCode(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(AppAuthenticationException.class)
    public ResponseEntity<ResponseMessageDTO> handleAuth(AppAuthenticationException ex) {
        ResponseMessageDTO response = new ResponseMessageDTO(
                LocalDateTime.now(),
                ex.getCode(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AppAccessDeniedException.class)
    public ResponseEntity<ResponseMessageDTO> handleAccessDenied(AppAccessDeniedException ex) {
        ResponseMessageDTO response = new ResponseMessageDTO(
                LocalDateTime.now(),
                ex.getCode(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(AppValidationException.class)
    public ResponseEntity<ResponseMessageDTO> handleValidation(AppValidationException ex) {
        ResponseMessageDTO response = new ResponseMessageDTO(
                LocalDateTime.now(),
                ex.getCode(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AppInvalidStateException.class)
    public ResponseEntity<ResponseMessageDTO> handleInvalidState(AppInvalidStateException ex) {
        ResponseMessageDTO response = new ResponseMessageDTO(
                LocalDateTime.now(),
                ex.getCode(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessageDTO> handleUnexpected(Exception ex) {

        log.error("An unexpected error occurred", ex);

        //Safe and generic message for the client
        ResponseMessageDTO response = new ResponseMessageDTO(
                LocalDateTime.now(),
                "UNEXPECTED_ERROR",
                "Something went wrong, Please try again later."
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


}
