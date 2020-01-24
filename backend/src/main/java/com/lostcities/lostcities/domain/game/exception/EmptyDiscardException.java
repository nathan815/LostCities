package com.lostcities.lostcities.domain.game.exception;

import com.lostcities.lostcities.domain.game.card.Color;

public class EmptyDiscardException extends IllegalStateException {
    public EmptyDiscardException(Color color) {
        super("Discard for " + color + " is empty");
    }
}
