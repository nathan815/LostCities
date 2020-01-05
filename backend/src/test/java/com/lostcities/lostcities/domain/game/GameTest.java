package com.lostcities.lostcities.domain.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {


    @Test
    public void constructGameWith2Players_shouldDrawPlayersStartingHands() {
        Player player1 = new Player(1L, "Player1");
        Player player2 = new Player(2L, "Player2");
        Game game = Game.create(123L, 1L, player1, player2);

        assertEquals("Player 1 has 8 cards", 8, game.getPlayer1().getHand().size());
        assertEquals("Player 2 has 8 cards", 8, game.getPlayer2().getHand().size());

        assertEquals(
                "Player 1's hand is valid",
                "[BLUE_3_0, RED_3_0, RED_2_0, YELLOW_6_0, GREEN_7_0, GREEN_1_1, WHITE_2_0, WHITE_1_0]",
                game.getPlayer1().getHand().toString()
        );

        assertEquals(
                "Player 2's hand is valid",
                "[WHITE_5_0, YELLOW_8_0, YELLOW_3_0, RED_1_2, GREEN_2_0, GREEN_1_0, GREEN_9_0, RED_1_0]",
                game.getPlayer2().getHand().toString());
    }
}
