package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.Color;
import com.lostcities.lostcities.domain.game.card.Deck;
import com.lostcities.lostcities.domain.game.exception.CardLowerValueException;
import com.lostcities.lostcities.domain.game.exception.CardNotInHandException;
import com.lostcities.lostcities.domain.game.exception.EmptyDeckException;
import com.lostcities.lostcities.domain.game.exception.EmptyDiscardException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class MoveTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Player player;

    private Card blue5Card = Card.expedition(Color.BLUE, 5);
    private Card green2Card = Card.expedition(Color.GREEN, 2);
    private Card yellow4Card = Card.expedition(Color.YELLOW, 4);
    private Card blueWagerCard = Card.wager(Color.BLUE, 0);

    @Before
    public void setup() {
        player = new Player(1L, "test player");
    }

    @Test
    public void execute_moveReadyToStart_shouldSetPlayerReadyToStart() {
        var move = Move.create(player, Move.Type.ReadyToStart);
        move.execute(new Deck(), new GameBoard());
        assertTrue(player.isReadyToStart());
    }

    @Test
    public void execute_moveReadyToStart_ifPlayerIsAlreadyReadyToStart_shouldThrowException() {
        player.setReadyToStart(true);
        var move = Move.create(player, Move.Type.ReadyToStart);
        thrown.expect(IllegalStateException.class);
        move.execute(new Deck(), new GameBoard());
    }

    @Test
    public void execute_ifDeckIsEmpty_shouldThrowException() {
        var move = Move.create(player, Move.Type.DrawDeck);

        thrown.expect(EmptyDeckException.class);

        move.execute(new Deck(), new GameBoard());
    }

    @Test
    public void execute_moveDrawFromEmptyRedDiscard_shouldThrowException() {
        player.addToHand(blue5Card);
        var deck = Deck.of(yellow4Card);
        var board = new GameBoard();
        var move = Move.create(player, Move.Type.DrawDiscard, Color.RED);

        assertTrue(board.getDiscardStack(Color.RED).isEmpty());

        thrown.expect(EmptyDiscardException.class);

        move.execute(deck, board);
    }

    @Test
    public void execute_movePlayCard_ifCardNotInHandHand_shouldThrowException() {
        var move = Move.create(player, Move.Type.PlayCard, blue5Card);

        thrown.expect(CardNotInHandException.class);
        move.execute(Deck.of(yellow4Card), new GameBoard());
    }

    @Test
    public void execute_moveDiscardCard_ifCardNotInHandHand_shouldThrowException() {
        var move = Move.create(player, Move.Type.DiscardCard, blue5Card);

        thrown.expect(CardNotInHandException.class);
        move.execute(Deck.of(yellow4Card), new GameBoard());
    }

    @Test
    public void execute_movePlayCard_ifCardIsLowerValueThanTopCard_shouldThrowException() {
        var green4Card = Card.expedition(Color.GREEN, 4);
        var green5Card = Card.expedition(Color.GREEN, 5);
        var deck = new Deck();
        var board = new GameBoard();

        player.addToHand(green4Card);
        player.addToHand(green5Card);

        var move1 = Move.create(player, Move.Type.PlayCard, green5Card);
        move1.execute(deck, board);

        // Playing Green4 isn't allowed because Green5 is in play
        var move2 = Move.create(player, Move.Type.PlayCard, green4Card);

        thrown.expect(CardLowerValueException.class);
        move2.execute(deck, board);
    }

    @Test
    public void execute_movePlayCard_shouldAddCardToCorrectCardPile() {
        player.addToHand(blue5Card);

        var move = Move.create(player, Move.Type.PlayCard, blue5Card);
        move.execute(new Deck(), new GameBoard());

        assertThat(player.getHand(), not(contains(blue5Card)));
        assertThat(player.getInPlay(Color.BLUE), contains(blue5Card));
    }

    @Test
    public void execute_moveDiscardCard_shouldAddToDiscardCardPileAndRemoveFromPlayerHand() {
        var deck = Deck.of(green2Card);
        var board = new GameBoard();
        player.addToHand(blue5Card);

        var move = Move.create(player, Move.Type.DiscardCard, blue5Card);
        move.execute(deck, board);

        assertThat(player.getHand(), not(contains(blue5Card)));
        assertThat(board.getDiscardStack(Color.BLUE), contains(blue5Card));
    }

    @Test
    public void execute_moveDrawFromDeck_shouldRemoveCardFromDeckAndAddToPlayerHand() {
        var deck = Deck.of(blueWagerCard, yellow4Card, green2Card);
        var board = new GameBoard();
        var move = Move.create(player, Move.Type.DrawDeck);

        move.execute(deck, board);

        assertThat(deck.getCards(), contains(blueWagerCard, yellow4Card));
        assertThat(deck.getCards(), not(contains(green2Card)));
        assertThat(player.getHand(), contains(green2Card));
    }

    @Test
    public void execute_moveDrawFromDiscard_shouldRemoveCardFromDiscardAndAddToPlayerHand() {
        var deck = new Deck();
        var board = new GameBoard();
        board.addToDiscard(blue5Card);

        var move = Move.create(player, Move.Type.DrawDiscard, Color.BLUE);

        move.execute(deck, board);

        assertThat(board.getDiscardStack(Color.BLUE), not(contains(blue5Card)));
        assertThat(player.getHand(), contains(blue5Card));
    }

}
