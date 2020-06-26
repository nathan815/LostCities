package com.lostcities.lostcities.domain.game.card;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class InPlayCardStackTest {

    private InPlayCardStack cards;

    @Before
    public void before() {
        cards = new InPlayCardStack();
    }

    @Test
    public void calculateValue_shouldBe0_whenStackIsEmpty() {
        assertThat(cards.calculateValue(), equalTo(0));
    }

    @Test
    public void calculateValue_shouldAddValuesOfCards() {
        cards.addToTop(Card.expedition(Color.BLUE, 3));
        cards.addToTop(Card.expedition(Color.BLUE, 4));
        cards.addToTop(Card.expedition(Color.BLUE, 8));

        int expected = -20 + 3 + 4 + 8; // -5
        assertThat(cards.calculateValue(), equalTo(expected)); //
    }

    @Test
    public void calculateValue_shouldCorrectlyCalculateWithExpeditionCardsAnd1Wager() {
        testWagersWithExpeditionCards(1);
    }

    @Test
    public void calculateValue_shouldCorrectlyCalculateWithExpeditionCardsAnd2Wagers() {
        testWagersWithExpeditionCards(2);
    }

    @Test
    public void calculateValue_shouldCorrectlyCalculateWithExpeditionCardsAnd3Wagers() {
        testWagersWithExpeditionCards(3);
    }

    @Test
    public void calculateValue_shouldCorrectlyCalculateWithNoExpeditionCardsAnd1Wager() {
        testWagersWithoutExpeditionCards(1);
    }

    @Test
    public void calculateValue_shouldCorrectlyCalculateWithNoExpeditionCardsAnd2Wagers() {
        testWagersWithoutExpeditionCards(2);
    }

    @Test
    public void calculateValue_shouldCorrectlyCalculateWithNoExpeditionCardsAnd3Wagers() {
        testWagersWithoutExpeditionCards(3);
    }

    private void testWagersWithExpeditionCards(int numWagerCards) {
        addWagersToCardStack(numWagerCards);
        cards.addToTop(Card.expedition(Color.BLUE, 3));
        cards.addToTop(Card.expedition(Color.BLUE, 4));
        cards.addToTop(Card.expedition(Color.BLUE, 8));

        int multiplier = 1 + numWagerCards;
        int expected = (-20 + 3 + 4 + 8) * multiplier;
        assertThat(cards.calculateValue(), equalTo(expected));
    }

    private void testWagersWithoutExpeditionCards(int numWagerCards) {
        addWagersToCardStack(numWagerCards);
        int multiplier = 1 + numWagerCards;
        int expected = -20 * multiplier;
        assertThat(cards.calculateValue(), equalTo(expected));
    }

    private void addWagersToCardStack(int numWagerCards) {
        for (int i = 0; i < numWagerCards; i++) {
            cards.addToTop(Card.wager(Color.BLUE, i));
        }
    }

}
