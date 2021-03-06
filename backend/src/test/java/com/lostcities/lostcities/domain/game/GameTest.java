package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.Color;
import com.lostcities.lostcities.domain.game.card.Deck;
import com.lostcities.lostcities.domain.game.exception.EmptyDeckException;
import com.lostcities.lostcities.domain.game.exception.GameNotStartedException;
import com.lostcities.lostcities.domain.game.exception.IllegalMoveException;
import com.lostcities.lostcities.domain.game.exception.NotPlayersTurnException;
import java.util.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.lostcities.lostcities.domain.game.card.CardTestUtils.cardSetFromStrings;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class GameTest {

    private static final long RANDOM_SEED = 1L;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Player player1;
    private Player player2;

    private Card red2Card = Card.expedition(Color.RED, 2);
    private Card red3Card = Card.expedition(Color.RED, 3);
    private Card blue3Card = Card.expedition(Color.BLUE, 3);

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
                "WHITE_7_0", "BLUE_9_0", "BLUE_6_0", "RED_0_2", "BLUE_7_0")));

        assertThat(game.getPlayer2().getHand(), is(cardSetFromStrings("RED_5_0", "BLUE_8_0", "WHITE_0_0", "YELLOW_2_0",
                "BLUE_0_1", "RED_0_1", "RED_2_0", "WHITE_9_0")));
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
    public void getCurrentTurnPlayer_ifGameNotYetStarted_shouldReturnOptionalEmpty() {
        Game game = Game.create(RANDOM_SEED, player1);
        assertEquals(Optional.empty(), game.getCurrentTurnPlayer());
    }

    @Test
    public void getCurrentTurnPlayer_ifGameJustStarted_shouldReturnPlayer1() {
        Game game = createGameAndStart();
        assertEquals(Optional.of(player1), game.getCurrentTurnPlayer());
    }

    @Test
    public void getPlayersStream_shouldNotContainNull() {
        Game game = Game.create(RANDOM_SEED, player1, null);
        assertThat(game.getPlayersStream().collect(toList()), not(contains(nullValue())));
    }

    @Test
    public void getPlayerById_ifPlayerIsInGame_shouldReturnPlayer() {
        Game game = Game.create(RANDOM_SEED, player1, null);
        assertEquals(Optional.of(player1), game.getPlayerById(player1.getId()));
    }

    @Test
    public void getPlayerById_ifPlayerIsNotInGame_shouldReturnEmptyOptional() {
        Game game = Game.create(RANDOM_SEED, player1, null);
        assertEquals(Optional.empty(), game.getPlayerById(200L));
    }

    @Test
    public void constructGameWith2Players_shouldHaveReadyToStartStatus() {
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
        Card red3 = Card.expedition(Color.RED, 3);
        player1.addToHand(red3);

        Game game = new Game(RANDOM_SEED, new Deck(), new GameBoard(), player1, player2);
        Move move = Move.create(player1, Move.Type.PlayCard, red3);

        thrown.expect(EmptyDeckException.class);

        game.makeMove(move);
    }

    @Test
    public void makeMove_ifNotPlayersTurn_shouldThrowException() {
        Card red2Card = Card.expedition(Color.RED, 2);
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
    public void makeMove_drawCardJustDiscarded_shouldThrowException() {
        Game game = createGameAndStart();
        game.setDeck(Deck.of(red2Card, red3Card));

        game.makeMove(Move.create(player1, Move.Type.DiscardCard, Card.expedition(Color.GREEN, 8)));

        thrown.expect(IllegalMoveException.class);
        game.makeMove(Move.create(player1, Move.Type.DrawDiscard, Color.GREEN));
    }

    @Test
    public void makeMove_shouldWorkForSeveralValidMoves() {
        Game game = createGameAndStart();

        int initialDeckSize = Deck.STARTING_SIZE - (2 * Player.HAND_SIZE);
        assertEquals(initialDeckSize, game.getDeck().size());

        game.makeMove(Move.create(player1, Move.Type.PlayCard, Card.expedition(Color.GREEN, 8)));
        game.makeMove(Move.create(player1, Move.Type.DrawDeck));

        game.makeMove(Move.create(player2, Move.Type.DiscardCard, Card.expedition(Color.RED, 5)));
        game.makeMove(Move.create(player2, Move.Type.DrawDeck));

        game.makeMove(Move.create(player1, Move.Type.DiscardCard, Card.expedition(Color.YELLOW, 5)));
        game.makeMove(Move.create(player1, Move.Type.DrawDiscard, Color.RED));

        game.makeMove(Move.create(player2, Move.Type.PlayCard, Card.expedition(Color.BLUE, 8)));
        game.makeMove(Move.create(player2, Move.Type.DrawDiscard, Color.YELLOW));

        int drawnCount = 2;
        assertEquals(initialDeckSize - drawnCount, game.getDeck().size());
        assertEquals(10, game.getMoves().size());
    }

    @Test
    public void makeMove_drawLastCardInDeck_shouldEndGame() {
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
