package com.lostcities.lostcities.domain.game.exception;

public class NotPlayersTurnException extends IllegalStateException {
    public NotPlayersTurnException(String playerName) {
        super("It is not " + playerName + "'s turn");
    }
}
