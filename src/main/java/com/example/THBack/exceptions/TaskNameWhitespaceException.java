package com.example.THBack.exceptions;

public class TaskNameWhitespaceException extends RuntimeException {
    public TaskNameWhitespaceException(){
        super("Введено недопустимое значение поля \"Тема задачи\".");
    }
}
