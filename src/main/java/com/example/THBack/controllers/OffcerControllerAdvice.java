package com.example.THBack.controllers;

import com.example.THBack.exceptions.EmployeeNotFoundException;
import com.example.THBack.exceptions.ApprovingYouOwnOfferException;
import com.example.THBack.exceptions.OfferNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OffcerControllerAdvice {

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(EmployeeNotFoundException ex) {
        return ex.getMessage();
    }
    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String offerNotFoundHandler(OfferNotFoundException ex) {
        return ex.getMessage();
    }
    @ExceptionHandler(ApprovingYouOwnOfferException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String approvingYouOwnOfferHandler(ApprovingYouOwnOfferException ex) {
        return ex.getMessage();
    }
}
