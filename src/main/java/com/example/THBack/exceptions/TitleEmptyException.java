package com.example.THBack.exceptions;

public class TitleEmptyException extends RuntimeException {
    public TitleEmptyException() {
        super("Поле \"Название\" обязательно для заполнения.");
    }
}
