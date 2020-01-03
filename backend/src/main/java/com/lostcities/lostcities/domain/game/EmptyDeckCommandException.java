package com.lostcities.lostcities.domain.game;

public class EmptyDeckCommandException extends CommandException {
    public EmptyDeckCommandException(String message) {
        super(message);
    }
}
