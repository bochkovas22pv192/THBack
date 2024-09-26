package com.example.THBack.exceptions;

public class ApprovingYouOwnOfferException extends RuntimeException {
    public ApprovingYouOwnOfferException() {
        super("You can't approve your own offer");
    }
}
