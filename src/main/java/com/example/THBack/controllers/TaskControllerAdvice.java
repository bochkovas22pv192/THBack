package com.example.THBack.controllers;

import com.example.THBack.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class TaskControllerAdvice {

    @ExceptionHandler(TaskNameEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String TaskNameEmptyHandler(TaskNameEmptyException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(TaskNameWhitespaceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String TaskNameWhitespaceHandler(TaskNameWhitespaceException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(TaskDescriptionEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String TaskDescriptionEmptyHandler(TaskDescriptionEmptyException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(TaskDescriptionWhitespaceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String TaskDescriptionWhitespaceHandler(TaskDescriptionWhitespaceException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(DateExecuteMinException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String DateExecuteMinHandler(DateExecuteMinException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(TaskEmailExecutorEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String TaskEmailExecutorEmptyHandler(TaskEmailExecutorEmptyException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(NoTaskException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String NoTaskHandler(NoTaskException ex) {
        return ex.getMessage();
    }
}
