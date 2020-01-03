package com.lostcities.lostcities.domain.game.card;

public class InvalidColorException extends RuntimeException {

    public InvalidColorException(String color) {
        super("Invalid color format: " + color);
    }
}
