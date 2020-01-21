package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;

public class CardLowerValueException extends IllegalStateException {
    public CardLowerValueException(Card playCard, Card topCard) {
        super("Cannot play card " + playCard + " as it is lower value than " + topCard);
    }
}
