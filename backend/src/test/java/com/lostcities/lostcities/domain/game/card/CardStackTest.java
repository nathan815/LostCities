package com.lostcities.lostcities.domain.game.card;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class CardStackTest {

    private Card green3Card = Card.expedition(Color.GREEN, 3),
            red4Card = Card.expedition(Color.RED, 4),
            yellow8Card = Card.expedition(Color.YELLOW, 8);

    @Test
    public void getTopCards_shouldReturnTopNCards() {
        CardStack stack = new CardStack();
        stack.addToTop(green3Card);
        stack.addToTop(red4Card);
        stack.addToTop(yellow8Card);

        assertThat(stack.getTopCards(1), contains(yellow8Card));
        assertThat(stack.getTopCards(2), contains(yellow8Card, red4Card));
        assertThat(stack.getTopCards(3), contains(yellow8Card, red4Card, green3Card));
    }

    @Test
    public void getTopCards_shouldReturnEmptyList_whenStackIsEmpty() {
        CardStack stack = new CardStack();
        assertThat(stack.getTopCards(1), is(emptyCollectionOf(Card.class)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTopCards_shouldThrowException_givenNegativeN() {
        new CardStack().getTopCards(-2);
    }
}