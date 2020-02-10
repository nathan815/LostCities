package com.lostcities.lostcities.domain.game.card;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class InPlayCardStackTest {

    private InPlayCardStack stack;

    @Before
    public void before() {
        stack = new InPlayCardStack();
    }

    @Test
    public void calculateValue_shouldBe0_whenStackIsEmpty() {
        assertThat(stack.calculateValue(), equalTo(0));
    }

    @Test
    public void calculateValue_shouldAddValuesOfCards() {
        stack.addToTop(Card.expedition(Color.BLUE, 3));
        stack.addToTop(Card.expedition(Color.BLUE, 4));
        stack.addToTop(Card.expedition(Color.BLUE, 8));

        int expected = -20 + 3 + 4 + 8; // -5
        assertThat(stack.calculateValue(), equalTo(expected)); //
    }

    @Test
    public void calculateValue_shouldMultiplyProperlyWith1Wager() {
        testWagers(1);
    }

    @Test
    public void calculateValue_shouldMultiplyProperlyWith2Wagers() {
        testWagers(2);
    }

    @Test
    public void calculateValue_shouldMultiplyProperlyWith3Wagers() {
        testWagers(3);
    }

    private void testWagers(int numWagerCards) {
        for (int i = 0; i < numWagerCards; i++) {
            stack.addToTop(Card.wager(Color.BLUE, i));
        }
        stack.addToTop(Card.expedition(Color.BLUE, 3));
        stack.addToTop(Card.expedition(Color.BLUE, 4));
        stack.addToTop(Card.expedition(Color.BLUE, 8));

        int multiplier = 1 + numWagerCards;
        int expected = (-20 + 3 + 4 + 8) * multiplier;
        assertThat(stack.calculateValue(), equalTo(expected));
    }

}
