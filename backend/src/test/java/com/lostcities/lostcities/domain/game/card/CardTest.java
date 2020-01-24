package com.lostcities.lostcities.domain.game.card;

import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void toString_returnsProperlyFormattedString() {
        Card card = new Card(Color.RED, 2);
        String cardString = card.toString();
        assertEquals(cardString, "RED_2_0");
    }

    @Test
    public void fromString_createsValidCardFromString() {
        Card expectedCard = new Card(Color.RED, 2);
        assertEquals(expectedCard, Card.fromString("RED_2_0"));
    }

    @Test(expected = UnableToParseCardException.class)
    public void fromString_shouldThrowForInvalidStringWithLessThan3Parts() {
        Card.fromString("RED_3");
    }

    @Test(expected = UnableToParseCardException.class)
    public void fromString_shouldThrowForInvalidStringWithMoreThan3Parts() {
        Card.fromString("RED_1_2_3");
    }

    @Test(expected = UnableToParseCardException.class)
    public void fromString_shouldThrowForInvalidStringWithNoUnderscores() {
        Card.fromString("RED");
    }

    @Test(expected = UnableToParseCardException.class)
    public void fromString_shouldThrowForInvalidStringWithNonIntegerCardValue() {
        Card.fromString("RED_HELLO_0");
    }

    @Test(expected = UnableToParseCardException.class)
    public void fromString_shouldThrowForInvalidStringWithNonIntegerInstance() {
        Card.fromString("RED_3_HELLO");
    }

    @Test
    public void testWagerCardsEquality() {
        // Wager cards are all numbered 1 but have different "instance" numbers
        assertEquals(Card.wager(Color.RED, 0), Card.wager(Color.RED, 0));
        assertNotEquals(Card.wager(Color.RED, 0), Card.wager(Color.RED, 1));
        assertNotEquals(Card.wager(Color.RED, 0), Card.wager(Color.BLUE, 0));
        assertFalse(Card.wager(Color.RED, 0).equals(null));
        assertFalse(Card.wager(Color.RED, 0).equals(new Object()));
    }

    @Test
    public void testExpeditionCardsEquality() {
        // Expedition cards are numbered 2-10
        assertEquals(Card.expedition(Color.RED, 3), Card.expedition(Color.RED, 3));
        assertEquals(Card.expedition(Color.GREEN, 2), Card.expedition(Color.GREEN, 2));
        assertNotEquals(Card.expedition(Color.RED, 3), Card.expedition(Color.RED, 4));
        assertNotEquals(Card.expedition(Color.RED, 3), Card.expedition(Color.BLUE, 3));
        assertNotEquals(Card.expedition(Color.RED, 4), Card.expedition(Color.GREEN, 5));
        assertNotEquals(Card.expedition(Color.BLUE, 5), Card.expedition(Color.BLUE, 10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createExpeditionCard_shouldThrowExceptionForValueLessThan2() {
        Card.expedition(Color.GREEN, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createExpeditionCard_shouldThrowExceptionForValueGreatThan10() {
        Card.expedition(Color.GREEN, 11);
    }

    @Test
    public void createWagerCard_shouldConstructCardWithCorrectValue() {
        assertEquals(Card.WAGER_CARD_VALUE, Card.wager(Color.RED, 0).getValue());
        assertEquals(Card.WAGER_CARD_VALUE, Card.wager(Color.RED, 1).getValue());
        assertEquals(Card.WAGER_CARD_VALUE, Card.wager(Color.RED, 2).getValue());
    }

    @Test
    public void isWager_shouldReturnTrueForWagerCard() {
        assertTrue(Card.wager(Color.RED, 2).isWager());
        assertTrue(Card.wager(Color.GREEN, 1).isWager());
    }

    @Test
    public void isWager_shouldReturnFalseForExpeditionCard() {
        assertFalse(Card.expedition(Color.RED, 10).isWager());
        assertFalse(Card.expedition(Color.GREEN, 3).isWager());
    }
}
