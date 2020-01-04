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
                "[BLUE_1_2, GREEN_5_0, WHITE_7_0, YELLOW_7_0, YELLOW_2_0, BLUE_1_1, RED_4_0, YELLOW_9_0]",
                game.getPlayer1().getHand().toString()
        );

        assertEquals(
                "Player 2's hand is valid",
                "[RED_7_0, YELLOW_1_1, WHITE_1_2, YELLOW_1_0, WHITE_1_1, RED_6_0, BLUE_5_0, GREEN_3_0]",
                game.getPlayer2().getHand().toString());
    }
}
