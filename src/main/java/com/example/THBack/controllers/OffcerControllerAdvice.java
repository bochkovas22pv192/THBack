package com.example.THBack.controllers;

import com.example.THBack.exceptions.*;
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

    @ExceptionHandler(TitleEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String TitleEmptyHandler(TitleEmptyException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(DescriptionEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String DescriptionEmptyHandler(DescriptionEmptyException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ImageSizeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String ImageSizeHandler(ImageSizeException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ImageCountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String ImageCountHandler(ImageCountException ex) {
        return ex.getMessage();
    }
}
