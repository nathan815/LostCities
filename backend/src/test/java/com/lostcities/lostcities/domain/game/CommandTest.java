package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.Color;
import com.lostcities.lostcities.domain.game.card.Deck;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class CommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    Player player;

    Card blue5Card = Card.createExpeditionCard(Color.BLUE, 5);
    Card green2Card = Card.createExpeditionCard(Color.GREEN, 2);
    Card yellow4Card = Card.createExpeditionCard(Color.YELLOW, 4);
    Card blueWagerCard = Card.createWagerCard(Color.BLUE, 0);

    @Before
    public void setup() {
        player = new Player(1L, "test player");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_bothPlayAndDiscardCard_shouldThrowException() {
        new Command(player,
                Card.createExpeditionCard(Color.RED, 3),
                Card.createExpeditionCard(Color.GREEN, 4),
                Color.BLUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_noPlayOrDiscardCard_shouldThrowException() {
        new Command(player, null, null, Color.BLUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_drawSameColorJustDiscarded_shouldThrowException() {
        new Command(player, null, blue5Card, Color.BLUE);
    }

    @Test
    public void execute_emptyDeck_shouldThrowException() throws CommandException {
        var command = new Command(player, blue5Card, null, Color.RED);

        thrown.expect(EmptyDeckCommandException.class);
        command.execute(new Deck(), new GameBoard());
    }

    @Test
    public void execute_drawFromEmptyRedDiscard_shouldThrowException() throws CommandException {
        player.addToHand(blue5Card);
        var deck = makeDeckFromCards(yellow4Card);
        var board = new GameBoard();
        var command = new Command(player, blue5Card, null, Color.RED);

        assertTrue(board.getDiscardStack(Color.RED).isEmpty());

        thrown.expect(CommandException.class);

        command.execute(deck, board);
    }

    @Test
    public void execute_playCardNotInHandHand_shouldThrowException() throws CommandException {
        var command = new Command(player, blue5Card, null, null);

        thrown.expect(CardNotInHandCommandException.class);
        command.execute(makeDeckFromCards(yellow4Card), new GameBoard());
    }

    @Test
    public void execute_discardCardNotInHandHand_shouldThrowException() throws CommandException {
        var command = new Command(player, null, blue5Card, null);

        thrown.expect(CardNotInHandCommandException.class);
        command.execute(makeDeckFromCards(yellow4Card), new GameBoard());
    }

    @Test
    public void execute_playCardAndDrawFromDeck_shouldAddCardToCorrectCardPileAndRemoveCardFromDeck() throws CommandException {
        var deck = makeDeckFromCards(green2Card);
        var board = new GameBoard();
        player.addToHand(blue5Card);

        var command = new Command(player, blue5Card, null, null);
        command.execute(deck, board);

        assertThat(deck.getCards(), not(contains(green2Card)));
        assertThat(player.getHand(), contains(green2Card));
        assertThat(player.getHand(), not(contains(blue5Card)));
        assertThat(board.getInPlayCardStack(Color.BLUE, player.getId()), contains(blue5Card));
    }

    @Test
    public void execute_playCardAndDrawFromDiscard_shouldAddCardToCorrectCardPileAndRemoveCardFromDiscard() throws CommandException {
        var deck = makeDeckFromCards(green2Card);
        var board = new GameBoard();
        board.addToDiscard(yellow4Card);
        player.addToHand(blue5Card);

        var command = new Command(player, blue5Card, null, Color.YELLOW);
        command.execute(deck, board);

        assertThat(deck.getCards(), contains(green2Card)); // did not draw from deck, so green 2 should still be there

        assertThat(board.getDiscardStack(Color.YELLOW), not(contains(yellow4Card)));
        assertThat(player.getHand(), contains(yellow4Card));

        assertThat(player.getHand(), not(contains(blue5Card)));
        assertThat(board.getInPlayCardStack(Color.BLUE, player.getId()), contains(blue5Card));
    }

    @Test
    public void execute_discardCardAndDrawFromDeck_shouldAddCardToCorrectDiscardCardPileAndRemoveCardFromDeck() throws CommandException {
        var deck = makeDeckFromCards(green2Card);
        var board = new GameBoard();
        player.addToHand(blue5Card);

        var command = new Command(player, null, blue5Card, null);
        command.execute(deck, board);

        assertThat(deck.getCards(), not(contains(green2Card)));
        assertThat(player.getHand(), contains(green2Card));

        assertThat(player.getHand(), not(contains(blue5Card)));
        assertThat(board.getDiscardStack(Color.BLUE), contains(blue5Card));
    }

    @Test
    public void execute_discardCardAndDrawFromDiscard_shouldAddCardToCorrectDiscardCardPileAndRemoveCardFromDiscard() throws CommandException {
        var deck = makeDeckFromCards(green2Card);
        var board = new GameBoard();
        board.addToDiscard(yellow4Card);
        player.addToHand(blue5Card);

        var command = new Command(player, null, blue5Card, Color.YELLOW);
        command.execute(deck, board);

        assertThat(deck.getCards(), contains(green2Card)); // did not draw from deck, so green 2 should still be there

        assertThat(board.getDiscardStack(Color.YELLOW), not(contains(yellow4Card)));
        assertThat(player.getHand(), contains(yellow4Card));

        assertThat(player.getHand(), not(contains(blue5Card)));
        assertThat(board.getDiscardStack(Color.BLUE), contains(blue5Card));
    }

    private Deck makeDeckFromCards(Card ...cards) {
        return new Deck(new ArrayList<>(Arrays.asList(cards)));
    }
}
