package com.example.THBack.exceptions;

public class ImageCountException extends RuntimeException {
    public ImageCountException() {
        super("Вы не можете загрузить больше 10 фото.");
    }
}
