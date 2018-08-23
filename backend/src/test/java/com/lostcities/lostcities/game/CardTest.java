package com.lostcities.lostcities.game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardTest {


    @Test
    public void fromString() {
        Card card = new Card(Color.RED, 1, 0);
        String cardString = card.toString();
        assertEquals(cardString, "RED_1_0");
        Card testCard = Card.fromString(cardString);
        assertEquals(testCard, card);

    }
}