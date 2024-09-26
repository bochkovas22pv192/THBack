package com.example.THBack.exceptions;

public class ImageSizeException extends RuntimeException {
    public ImageSizeException() {
        super("Данное фото нельзя загрузить, загрузите фото меньшим размером.");
    }
}
