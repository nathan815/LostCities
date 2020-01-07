package com.lostcities.lostcities.domain.game;

public class EmptyDeckMoveException extends MoveException {
    public EmptyDeckMoveException(String message) {
        super(message);
    }
}
