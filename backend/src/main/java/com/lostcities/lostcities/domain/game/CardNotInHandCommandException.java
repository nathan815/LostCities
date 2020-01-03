package com.lostcities.lostcities.domain.game;

public class CardNotInHandCommandException extends CommandException {
    public CardNotInHandCommandException(String message) {
        super(message);
    }
}
