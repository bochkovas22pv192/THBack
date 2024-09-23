package com.example.THBack.exceptions;

public class TaskEmailExecutorEmptyException extends RuntimeException {
    public TaskEmailExecutorEmptyException(){
        super("Поле \"Исполнитель задачи\" обязательно для заполнения.");
    }
}
