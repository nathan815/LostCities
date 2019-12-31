package com.lostcities.lostcities.domain.model.game;

public class UnableToParseCardException extends RuntimeException {

    public UnableToParseCardException(String message) {
        super(message);
    }

    public UnableToParseCardException(String card, Throwable throwable) {
        super("Unable to parse card from string: " + card, throwable);
    }
}
