package com.lostcities.lostcities.domain.model.game;

import com.lostcities.lostcities.domain.model.game.card.Card;
import com.lostcities.lostcities.domain.model.game.card.Color;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;
    private Card red2Card = Card.createExpeditionCard(Color.RED, 2);
    private Card blue4Card = Card.createExpeditionCard(Color.BLUE, 4);

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
    public void removeFromHand_shouldRemoveCardAndReturnTrue() {
        player.addToHand(red2Card);
        assertThat(player.getHand(), contains(red2Card));
        assertTrue(player.removeFromHand(red2Card));
        assertThat(player.getHand(), not(contains(red2Card)));
    }

    @Test
    public void removeFromHand_shouldReturnFalseIfCardIsNotInHand() {
        assertFalse(player.removeFromHand(red2Card));
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
}
