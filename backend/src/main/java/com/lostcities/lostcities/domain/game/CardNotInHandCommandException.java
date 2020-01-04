package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;

public class CardNotInHandCommandException extends CommandException {
    public CardNotInHandCommandException(String message) {
        super(message);
    }

    public CardNotInHandCommandException(Card card) {
        super("Player does not have card " + card + " in hand");
    }
}
