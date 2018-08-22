package com.lostcities.lostcities.game.exceptions;

public class InvalidColorException extends RuntimeException {

    public InvalidColorException(String color) {
        super("Invalid color format: " + color);
    }
}
