package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;

public class CardNotInHandException extends IllegalStateException {
    public CardNotInHandException(String message) {
        super(message);
    }

    public CardNotInHandException(Card card) {
        super("Player does not have card " + card + " in hand");
    }
}
