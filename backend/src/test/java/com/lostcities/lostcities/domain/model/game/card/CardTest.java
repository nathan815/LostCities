package com.lostcities.lostcities.domain.model.game.card;

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
    public void fromString_shouldThrowForInvalidStringWithNonIntegerCardNumber() {
        Card.fromString("RED_HELLO_0");
    }

    @Test(expected = UnableToParseCardException.class)
    public void fromString_shouldThrowForInvalidStringWithNonIntegerInstance() {
        Card.fromString("RED_3_HELLO");
    }

    @Test
    public void testWagerCardsEquality() {
        // Wager cards are all numbered 1 but have different "instance" numbers
        assertEquals(Card.createWagerCard(Color.RED, 0), Card.createWagerCard(Color.RED, 0));
        assertNotEquals(Card.createWagerCard(Color.RED, 0), Card.createWagerCard(Color.RED, 1));
        assertNotEquals(Card.createWagerCard(Color.RED, 0), Card.createWagerCard(Color.BLUE, 0));
        assertFalse(Card.createWagerCard(Color.RED, 0).equals(null));
        assertFalse(Card.createWagerCard(Color.RED, 0).equals(new Object()));
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
