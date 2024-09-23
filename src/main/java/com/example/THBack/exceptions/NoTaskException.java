package com.example.THBack.exceptions;

public class NoTaskException extends RuntimeException {
    public NoTaskException(){
        super("Нет такой задачи.");
    }
}
