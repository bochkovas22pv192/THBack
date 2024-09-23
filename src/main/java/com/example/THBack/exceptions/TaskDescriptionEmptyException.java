package com.example.THBack.exceptions;

public class TaskDescriptionEmptyException extends RuntimeException {
    public TaskDescriptionEmptyException(){
        super("Поле \"Комментарий\" обязательно для заполнения.");
    }
}
