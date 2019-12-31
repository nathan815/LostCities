package com.lostcities.lostcities.domain.card;

import com.lostcities.lostcities.domain.model.game.Card;
import com.lostcities.lostcities.domain.model.game.Color;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardTest {


    @Test
    public void toString_returnsProperlyFormattedString() {
        Card card = new Card(Color.RED, 1, 0);
        String cardString = card.toString();
        assertEquals(cardString, "RED_1_0");
    }

    @Test
    public void fromString_createsValidCardFromString() {
        Card card = new Card(Color.RED, 1, 0);
        String cardString = card.toString();
        Card testCard = Card.fromString(cardString);
        assertEquals(testCard, card);
    }
}
