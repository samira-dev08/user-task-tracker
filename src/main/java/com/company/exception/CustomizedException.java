package com.company.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class CustomizedException  {

    @ExceptionHandler(UserNotFoundException.class)
    public final ErrorDetails handleUserError(UserNotFoundException ex){
        return ErrorDetails.builder()
                .uniqueExceptionId(UUID.randomUUID())
                .message(ex.getMessage())
                .timeStamp(LocalDate.now())
                .build();
    }
    @ExceptionHandler(TaskNotFoundException.class)
    public final ErrorDetails handleTaskError(TaskNotFoundException ex){
        return ErrorDetails.builder()
                .uniqueExceptionId(UUID.randomUUID())
                .message(ex.getMessage())
                .timeStamp(LocalDate.now())
                .build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<?> handleInvalidArgument(MethodArgumentNotValidException ex)  {
        Map<String, String> errorMap =  new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return new ResponseEntity<>(errorMap,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public final ErrorDetails handleGlobalError(Exception ex){
        return ErrorDetails.builder()
                .uniqueExceptionId(UUID.randomUUID())
                .message(ex.getMessage())
                .timeStamp(LocalDate.now())
                .build();
    }
}
