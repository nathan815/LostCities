package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.Color;
import com.lostcities.lostcities.domain.game.card.Deck;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.lostcities.lostcities.domain.game.card.CardTestUtils.cardSetFromStrings;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class GameTest {

    private static final long RANDOM_SEED = 1L;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Player player1;
    private Player player2;

    @Before
    public void before() {
        player1 = new Player(1L, "Player1 Name");
        player2 = new Player(2L, "Player2 Name");
    }

    @Test
    public void start_shouldDrawPlayersStartingHandsAndChangeStatus() {
        Game game = Game.create(RANDOM_SEED, player1, player2);

        game.start();

        assertEquals(Game.Status.Started, game.getStatus());

        assertThat(game.getPlayer1().getHand(), is(cardSetFromStrings("GREEN_8_0", "YELLOW_6_0", "YELLOW_5_0",
                "WHITE_7_0", "BLUE_9_0", "BLUE_6_0", "RED_1_2", "BLUE_7_0")));

        assertThat(game.getPlayer2().getHand(), is(cardSetFromStrings("RED_5_0", "BLUE_8_0", "WHITE_1_0", "YELLOW_2_0",
                "BLUE_1_1", "RED_1_1", "RED_2_0", "WHITE_9_0")));
    }

    @Test
    public void start_player1NotSet_shouldThrowException() {
        Game game = Game.create(RANDOM_SEED, null, player2);
        thrown.expect(IllegalStateException.class);
        game.start();
    }

    @Test
    public void start_player2NotSet_shouldThrowException() {
        Game game = Game.create(RANDOM_SEED, player1);
        thrown.expect(IllegalStateException.class);
        game.start();
    }

    @Test
    public void joinGameAsSecondPlayer_shouldUpdatePlayer2AndChangeStatus() {
        Game game = Game.create(RANDOM_SEED, player1);
        game.joinGameAsSecondPlayer(player2);

        assertEquals(player2, game.getPlayer2());
        assertEquals(Game.Status.ReadyToStart, game.getStatus());
    }

    @Test
    public void gameWith2Players_shouldHaveReadyToStartStatus() {
        Game game = Game.create(RANDOM_SEED, player1, player2);
        assertEquals(player1, game.getPlayer1());
        assertEquals(player2, game.getPlayer2());
        assertEquals(Game.Status.ReadyToStart, game.getStatus());
    }

    @Test
    public void makeMove_emptyDeck_shouldThrowException() {
        Card red3 = Card.createExpeditionCard(Color.RED, 3);
        player1.addToHand(red3);

        Game game = new Game(1L, new Deck(), new GameBoard(), player1, player2);
        Move move = Move.create(player1, Move.Type.PlayCard, red3);

        thrown.expect(EmptyDeckException.class);

        game.makeMove(move);
    }

    @Test
    public void makeMove_drawLastCardInDeck_shouldEndGame() {
        Card red3 = Card.createExpeditionCard(Color.RED, 3);
        Game game = new Game(1L, Deck.of(red3), new GameBoard(), player1, player2);

        game.makeMove(Move.create(player1, Move.Type.DrawDeck));

        assertTrue(game.getDeck().isEmpty());
        assertEquals(Game.Status.Ended, game.getStatus());
    }

}
