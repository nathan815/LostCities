package com.lostcities.lostcities.domain.game;

public class IllegalMoveException extends IllegalStateException {
    public IllegalMoveException(String s) {
        super(s);
    }
}
