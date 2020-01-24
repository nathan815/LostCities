package com.lostcities.lostcities.domain.game;

public class GameNotStartedException extends IllegalStateException {
    public GameNotStartedException() {
        super("Game has not yet been started");
    }
}
