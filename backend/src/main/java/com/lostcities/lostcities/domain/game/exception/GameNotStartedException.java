package com.lostcities.lostcities.domain.game.exception;

public class GameNotStartedException extends IllegalStateException {
    public GameNotStartedException() {
        super("Game has not yet been started");
    }
}
