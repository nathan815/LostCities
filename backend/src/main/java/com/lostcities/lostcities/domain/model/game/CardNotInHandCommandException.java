package com.lostcities.lostcities.domain.model.game;

public class CardNotInHandCommandException extends CommandException {
    public CardNotInHandCommandException(String message) {
        super(message);
    }
}
