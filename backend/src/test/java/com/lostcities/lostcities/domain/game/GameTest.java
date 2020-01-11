package com.lostcities.lostcities.domain.game;

import org.junit.Test;

import static com.lostcities.lostcities.domain.game.card.CardTestUtils.cardSetFromStrings;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void constructGameWith2Players_shouldDrawPlayersStartingHands() {
        Player player1 = new Player(1L, "Player1");
        Player player2 = new Player(2L, "Player2");
        Game game = Game.create(123L, 1L, Game.Status.Started, player1, player2);

        assertThat(game.getPlayer1().getHand(), is(cardSetFromStrings("BLUE_3_0", "RED_3_0", "RED_2_0", "YELLOW_6_0",
                "GREEN_7_0", "GREEN_1_1", "WHITE_2_0", "WHITE_1_0")));

        assertThat(game.getPlayer2().getHand(), is(cardSetFromStrings("WHITE_5_0", "YELLOW_8_0", "YELLOW_3_0", "RED_1_2",
                "GREEN_2_0", "GREEN_1_0", "GREEN_9_0", "RED_1_0")));
    }

}
