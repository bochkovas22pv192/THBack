package com.example.THBack.exceptions;

public class TaskNameEmptyException extends RuntimeException {
    public TaskNameEmptyException(){
        super("Поле \"Название задачи\" обязательно для заполнения.");
    }
}
