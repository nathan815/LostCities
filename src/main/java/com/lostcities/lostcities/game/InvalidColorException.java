package com.lostcities.lostcities.game;

public class InvalidColorException extends RuntimeException {

    public InvalidColorException(String color) {
        super("Invalid color format: " + color);
    }
}
