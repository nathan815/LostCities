package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;

public class CannotPlayLowerValueCardException extends RuntimeException {
    public CannotPlayLowerValueCardException(Card playCard, Card topCard) {
        super("Cannot play card " + playCard + " as it is lower value than " + topCard);
    }
}
