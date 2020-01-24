package com.lostcities.lostcities.domain.game.exception;

public class EmptyDeckException extends IllegalStateException {
    public EmptyDeckException(String message) {
        super(message);
    }

    public EmptyDeckException() {
        super("Deck is empty");
    }
}
