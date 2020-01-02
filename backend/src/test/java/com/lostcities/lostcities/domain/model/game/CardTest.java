package com.lostcities.lostcities.domain.model.game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testWagerCardsEquality() {
        // Wager cards are all numbered 1 but have different "instance" numbers
        assertEquals(Card.createWagerCard(Color.RED, 0), Card.createWagerCard(Color.RED, 0));
        assertNotEquals(Card.createWagerCard(Color.RED, 0), Card.createWagerCard(Color.RED, 1));
        assertNotEquals(Card.createWagerCard(Color.RED, 0), Card.createWagerCard(Color.BLUE, 0));
    }

    @Test
    public void testExpeditionCardsEquality() {
        // Expedition cards are numbered 2-10
        assertEquals(Card.createExpeditionCard(Color.RED, 3), Card.createExpeditionCard(Color.RED, 3));
        assertEquals(Card.createExpeditionCard(Color.GREEN, 2), Card.createExpeditionCard(Color.GREEN, 2));
        assertNotEquals(Card.createExpeditionCard(Color.RED, 3), Card.createExpeditionCard(Color.RED, 4));
        assertNotEquals(Card.createExpeditionCard(Color.RED, 3), Card.createExpeditionCard(Color.BLUE, 3));
        assertNotEquals(Card.createExpeditionCard(Color.RED, 4), Card.createExpeditionCard(Color.GREEN, 5));
        assertNotEquals(Card.createExpeditionCard(Color.BLUE, 5), Card.createExpeditionCard(Color.BLUE, 10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createExpeditionCard_shouldThrowExceptionForNumberLessThan2() {
        Card.createExpeditionCard(Color.GREEN, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createExpeditionCard_shouldThrowExceptionForNumberGreatThan10() {
        Card.createExpeditionCard(Color.GREEN, 11);
    }

    @Test
    public void createWagerCard_shouldConstructCardWithNumber1() {
        assertEquals(1, Card.createWagerCard(Color.RED, 0).getNumber());
        assertEquals(1, Card.createWagerCard(Color.RED, 1).getNumber());
        assertEquals(1, Card.createWagerCard(Color.RED, 2).getNumber());
    }

    @Test
    public void isWager_shouldReturnTrueForWagerCard() {
        assertTrue(Card.createWagerCard(Color.RED, 2).isWager());
        assertTrue(Card.createWagerCard(Color.GREEN, 1).isWager());
    }

    @Test
    public void isWager_shouldReturnFalseForExpeditionCard() {
        assertFalse(Card.createExpeditionCard(Color.RED, 10).isWager());
        assertFalse(Card.createExpeditionCard(Color.GREEN, 3).isWager());
    }
}
