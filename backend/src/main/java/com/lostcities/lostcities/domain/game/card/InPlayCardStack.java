package com.lostcities.lostcities.domain.game.card;

public class InPlayCardStack extends CardStack {

    private final static int INITIAL_EXPEDITION_VALUE = -20;

    public int calculateScore() {
        if (getCards().isEmpty()) {
            return 0;
        }
        int value = INITIAL_EXPEDITION_VALUE, multiplier = 1;
        for(Card card : getCards()) {
            if(card.isWager()) multiplier++;
            else value += card.getValue();
        }
        return value * multiplier;
    }

}
