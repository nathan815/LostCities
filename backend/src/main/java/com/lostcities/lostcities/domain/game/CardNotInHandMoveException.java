package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;

public class CardNotInHandMoveException extends MoveException {
    public CardNotInHandMoveException(String message) {
        super(message);
    }

    public CardNotInHandMoveException(Card card) {
        super("Player does not have card " + card + " in hand");
    }
}
