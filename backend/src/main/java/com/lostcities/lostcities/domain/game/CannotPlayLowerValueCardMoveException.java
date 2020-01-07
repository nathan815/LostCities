package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;

public class CannotPlayLowerValueCardMoveException extends MoveException {
    public CannotPlayLowerValueCardMoveException(Card playCard, Card topCard) {
        super("Cannot play card " + playCard + " as it is lower value than " + topCard);
    }
}
