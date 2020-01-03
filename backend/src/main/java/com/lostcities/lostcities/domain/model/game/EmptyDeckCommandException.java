package com.lostcities.lostcities.domain.model.game;

public class EmptyDeckCommandException extends CommandException {
    public EmptyDeckCommandException(String message) {
        super(message);
    }
}
