package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.Color;
import com.lostcities.lostcities.domain.game.card.Deck;
import com.lostcities.lostcities.domain.game.exception.EmptyDeckException;
import com.lostcities.lostcities.domain.game.exception.GameNotStartedException;
import com.lostcities.lostcities.domain.game.exception.IllegalMoveException;
import com.lostcities.lostcities.domain.game.exception.NotPlayersTurnException;
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
    public void whenBothPlayersAreReady_shouldStartGame() {
        Game game = createGameAndStart();

        assertEquals(Game.Status.Started, game.getStatus());

        assertThat(game.getPlayer1().getHand(), is(cardSetFromStrings("GREEN_8_0", "YELLOW_6_0", "YELLOW_5_0",
                "WHITE_7_0", "BLUE_9_0", "BLUE_6_0", "RED_1_2", "BLUE_7_0")));

        assertThat(game.getPlayer2().getHand(), is(cardSetFromStrings("RED_5_0", "BLUE_8_0", "WHITE_1_0", "YELLOW_2_0",
                "BLUE_1_1", "RED_1_1", "RED_2_0", "WHITE_9_0")));
    }

    @Test
    public void start_ifPlayer2NotSet_shouldThrowException() {
        Game game = Game.create(RANDOM_SEED, player1);
        game.makeMove(Move.create(player1, Move.Type.ReadyToStart));
        thrown.expect(IllegalStateException.class);
        game.makeMove(Move.create(player2, Move.Type.ReadyToStart));
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
    public void makeMove_ReadyToStart_ifGameNotStarted_shouldNotThrowException() {
        Game game = Game.create(RANDOM_SEED, player1, player2);
        game.makeMove(Move.create(player1, Move.Type.ReadyToStart));
        game.makeMove(Move.create(player2, Move.Type.ReadyToStart));
    }

    @Test
    public void makeMove_ifPlayerNotInGame_shouldThrowException() {
        Game game = Game.create(RANDOM_SEED, player1, player2);
        thrown.expect(IllegalStateException.class);
        game.makeMove(Move.create(new Player(3, "hi"), Move.Type.ReadyToStart));
    }

    @Test
    public void makeMove_ifGameNotStarted_shouldThrowException() {
        Game game = Game.create(RANDOM_SEED, player1, player2);
        thrown.expect(GameNotStartedException.class);
        game.makeMove(Move.create(player1, Move.Type.DrawDeck));
    }

    @Test
    public void makeMove_ifEmptyDeck_shouldThrowException() {
        Card red3 = Card.createExpeditionCard(Color.RED, 3);
        player1.addToHand(red3);

        Game game = new Game(RANDOM_SEED, new Deck(), new GameBoard(), player1, player2);
        Move move = Move.create(player1, Move.Type.PlayCard, red3);

        thrown.expect(EmptyDeckException.class);

        game.makeMove(move);
    }

    @Test
    public void makeMove_ifNotPlayersTurn_shouldThrowException() {
        Card red2Card = Card.createExpeditionCard(Color.RED, 2);
        Game game = createGameAndStart();

        thrown.expect(NotPlayersTurnException.class);
        game.makeMove(Move.create(player2, Move.Type.PlayCard, red2Card));
    }

    @Test
    public void makeMove_drawBeforePlayOrDiscard_shouldThrowException() {
        Game game = createGameAndStart();

        thrown.expect(IllegalMoveException.class);
        game.makeMove(Move.create(player1, Move.Type.DrawDeck));
    }

    @Test
    public void makeMove_shouldWorkForSeveralValidMoves() {
        Card red2Card = Card.createExpeditionCard(Color.RED, 2);
        Card red3Card = Card.createExpeditionCard(Color.RED, 3);
        Card blue3Card = Card.createExpeditionCard(Color.BLUE, 3);
        Game game = createGameAndStart();

        int initialDeckSize = Deck.STARTING_SIZE - (2 * Player.HAND_SIZE);
        assertEquals(initialDeckSize, game.getDeck().size());

        player1.addToHand(red2Card);
        player1.addToHand(red3Card);
        player2.addToHand(blue3Card);

        game.makeMove(Move.create(player1, Move.Type.PlayCard, red2Card));
        game.makeMove(Move.create(player1, Move.Type.DrawDeck));

        game.makeMove(Move.create(player2, Move.Type.DiscardCard, blue3Card));
        game.makeMove(Move.create(player2, Move.Type.DrawDeck));

        game.makeMove(Move.create(player1, Move.Type.PlayCard, red3Card));
        game.makeMove(Move.create(player1, Move.Type.DrawDiscard, Color.BLUE));

        int drawnCount = 2;
        assertEquals(initialDeckSize - drawnCount, game.getDeck().size());
    }

    @Test
    public void makeMove_drawLastCardInDeck_shouldEndGame() {
        Card red3Card = Card.createExpeditionCard(Color.RED, 3);
        Card blue3Card = Card.createExpeditionCard(Color.BLUE, 3);
        Game game = createGameAndStart();
        game.setDeck(Deck.of(red3Card)); // 1 card in deck

        player1.addToHand(blue3Card);
        game.makeMove(Move.create(player1, Move.Type.PlayCard, blue3Card));
        game.makeMove(Move.create(player1, Move.Type.DrawDeck));

        assertTrue(game.getDeck().isEmpty());
        assertEquals(Game.Status.Ended, game.getStatus());
    }

    private Game createGameAndStart() {
        Game game = Game.create(RANDOM_SEED, player1, player2);
        bothPlayersReadyToStart(game);
        return game;
    }

    private void bothPlayersReadyToStart(Game game) {
        game.makeMove(Move.create(player1, Move.Type.ReadyToStart));
        game.makeMove(Move.create(player2, Move.Type.ReadyToStart));
    }

}
