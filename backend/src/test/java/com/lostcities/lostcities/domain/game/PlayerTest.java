package com.lostcities.lostcities.domain.game;

import  com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.Color;
import com.lostcities.lostcities.domain.game.exception.CardNotInHandException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;
    private Card red2Card = Card.expedition(Color.RED, 2);
    private Card blue4Card = Card.expedition(Color.BLUE, 4);

    @Before
    public void setup() {
        player = new Player(1L, "Bob");
    }

    @Test
    public void addToHand_shouldAddCardToHand() {
        assertThat(player.getHand(), is(empty()));
        player.addToHand(red2Card);
        assertThat(player.getHand(), contains(red2Card));
    }

    @Test
    public void addToHand_shouldAddMultipleCardsToHand() {
        assertThat(player.getHand(), is(empty()));
        player.addToHand(red2Card);
        player.addToHand(blue4Card);
        assertThat(player.getHand(), contains(red2Card, blue4Card));
    }

    @Test
    public void removeFromHand_shouldRemoveCardFromHand() {
        player.addToHand(red2Card);
        assertThat(player.getHand(), contains(red2Card));
        player.removeFromHand(red2Card);
        assertThat(player.getHand(), not(contains(red2Card)));
    }

    @Test(expected = CardNotInHandException.class)
    public void removeFromHand_shouldThrowException_whenCardIsNotInHand() {
        player.removeFromHand(red2Card);
    }

    @Test(expected = CardNotInHandException.class)
    public void play_shouldThrowException_whenCardIsNotInHand() {
        player.play(blue4Card);
    }

    @Test
    public void play_shouldAddCardToInPlayAndRemoveCardFromHand() {
        player.addToHand(blue4Card);
        player.play(blue4Card);
        assertThat(player.getInPlay(Color.BLUE), contains(blue4Card));
        assertThat(player.getHand(), not(contains(blue4Card)));
    }

    @Test
    public void playerEquality() {
        assertEquals(player, player);
        assertNotEquals(player, null);
        assertEquals(new Player(1L, "Bobby"), new Player(1L, "Bobby Tables"));
        assertNotEquals(new Player(1L, "Jill"), new Player(2L, "Jill"));
    }

    @Test
    public void playerHashCode() {
        assertEquals(new Player(1L, "Bobby").hashCode(), new Player(1L, "Bobby Tables").hashCode());
        assertNotEquals(new Player(1L, "Bobby").hashCode(), new Player(2L, "Bobby").hashCode());
    }

    @Test
    public void calculateScore_ifPlayerHasNoCardsInPlay_shouldReturn0() {
        assertEquals(0, player.calculateScore());
    }

    @Test
    public void calculateScore_shouldProperlyCalculateScore() {
        // First set up player's hand
        player.addToHand(Card.expedition(Color.BLUE, 9));
        player.addToHand(Card.wager(Color.GREEN, 0));
        player.addToHand(Card.expedition(Color.GREEN, 5));
        player.addToHand(Card.expedition(Color.GREEN, 7));
        player.addToHand(Card.expedition(Color.GREEN, 8));
        player.addToHand(Card.expedition(Color.GREEN, 10));

        // Now play some cards

        // blue score = -20 + 9 = -11
        player.play(Card.expedition(Color.BLUE, 9));

        // green score = (-20 + 5 + 7 + 8 + 10) * 2 = 20
        player.play(Card.wager(Color.GREEN, 0));
        player.play(Card.expedition(Color.GREEN, 5));
        player.play(Card.expedition(Color.GREEN, 7));
        player.play(Card.expedition(Color.GREEN, 8));
        player.play(Card.expedition(Color.GREEN, 10));

        // total score = -11 + 20 = 9
        assertEquals(9, player.calculateScore());
    }
}
