package com.nuzhd.taskmanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {
            TaskNotFoundException.class,
            UserNotFoundException.class,
            IllegalArgumentException.class,
            UsernameTakenException.class,
    })
    public ResponseEntity<Object> handleSimilarException(RuntimeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        MyFieldError[] fieldErrors = new MyFieldError[ex.getErrorCount()];
        for (int i = 0; i < ex.getFieldErrors().size(); i++) {
            FieldError error = ex.getFieldErrors().get(i);
            fieldErrors[i] = new MyFieldError(
                    error.getField(),
                    error.getDefaultMessage()
            );
        }
        errorResponse.put("error", "Validation failed!");
        errorResponse.put("errors", fieldErrors);
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<Object> handleInvalidJWTException(RuntimeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotEnoughPermissionsException.class)
    public ResponseEntity<Object> handleNotEnoughPermissions(RuntimeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }


}
