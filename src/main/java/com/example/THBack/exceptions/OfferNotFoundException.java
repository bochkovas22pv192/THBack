package com.example.THBack.exceptions;

public class OfferNotFoundException extends RuntimeException {

    public OfferNotFoundException(Long id) {
        super("Could not find offer " + id);
    }
}
