package com.lostcities.lostcities.domain.game.card;

import com.google.common.collect.Lists;
import java.util.Optional;
import java.util.Random;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeckTest {

    @Test
    public void getShuffledDeck_returnsProperlyShuffledDeckGivenASeed() {
        Deck deck = Deck.getShuffledDeck(new Random(1L));
        String expectedCards = "BLUE_1_2,RED_7_0,GREEN_5_0,YELLOW_1_1,WHITE_7_0,WHITE_1_2,YELLOW_7_0,YELLOW_1_0," +
                "YELLOW_2_0,WHITE_1_1,BLUE_1_1,RED_6_0,RED_4_0,BLUE_5_0,YELLOW_9_0,GREEN_3_0,GREEN_4_0,GREEN_8_0," +
                "YELLOW_5_0,RED_9_0,BLUE_1_0,RED_5_0,YELLOW_1_2,GREEN_6_0,WHITE_8_0,BLUE_6_0,RED_8_0,BLUE_9_0,WHITE_4_0," +
                "WHITE_6_0,WHITE_3_0,GREEN_1_2,BLUE_7_0,BLUE_8_0,RED_1_1,YELLOW_4_0,BLUE_4_0,BLUE_2_0,WHITE_9_0,RED_1_0," +
                "WHITE_1_0,GREEN_9_0,WHITE_2_0,GREEN_1_0,GREEN_1_1,GREEN_2_0,GREEN_7_0,RED_1_2,YELLOW_6_0,YELLOW_3_0," +
                "RED_2_0,YELLOW_8_0,RED_3_0,WHITE_5_0,BLUE_3_0";
        assertEquals(expectedCards, deck.toString());
    }

    @Test
    public void draw_alwaysReturnsAndRemovesFirstCard() {
        var cards = Lists.newArrayList(
                new Card(Color.RED, 2),
                new Card(Color.BLUE, 3));
        Deck deck = Deck.fromList(cards);
        assertEquals(Optional.of(cards.get(1)), deck.draw());
        assertEquals(Optional.of(cards.get(0)), deck.draw());
        assertEquals(Optional.empty(), deck.draw());
        assertEquals(0, deck.size());
        assertTrue(deck.isEmpty());
    }

    @Test
    public void draw_returnsEmptyOptionalWhenDeckIsEmpty() {
        Deck emptyDeck = new Deck();
        assertTrue(emptyDeck.isEmpty());
        assertEquals(Optional.empty(), emptyDeck.draw());
    }

    @Test
    public void draw_givenCard_removesAndReturnsCard() {
        var red2Card = Card.createExpeditionCard(Color.RED, 3);
        var blue2Card = Card.createExpeditionCard(Color.BLUE, 3);
        Deck deck = Deck.fromList(Lists.newArrayList((red2Card)));
        assertTrue(deck.draw(red2Card));
        assertFalse(deck.draw(blue2Card));
    }

}
