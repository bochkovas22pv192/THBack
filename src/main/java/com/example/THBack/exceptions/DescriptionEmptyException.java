package com.example.THBack.exceptions;

public class DescriptionEmptyException extends RuntimeException {
    public DescriptionEmptyException() {
        super("Поле \"Описание\" обязательно для заполнения.");
    }
}
