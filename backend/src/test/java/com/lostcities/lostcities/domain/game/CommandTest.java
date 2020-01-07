package com.lostcities.lostcities.domain.game;

import com.google.common.collect.Lists;
import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.Color;
import com.lostcities.lostcities.domain.game.card.Deck;
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
    public void constructor_bothPlayAndDiscardCardSet_shouldThrowException() {
        Command.builder().player(player).playCard(blue5Card).discardCard(yellow4Card).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_noPlayOrDiscardCard_shouldThrowException() {
        Command.builder().player(player).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_drawSameColorJustDiscarded_shouldThrowException() {
        Command.builder().player(player).discardCard(blue5Card).drawDiscardColor(Color.BLUE).build();
    }

    @Test
    public void execute_emptyDeck_shouldThrowException() throws CommandException {
        var command = Command.builder().player(player).playCard(blue5Card).drawDiscardColor(Color.RED).build();

        thrown.expect(EmptyDeckCommandException.class);

        command.execute(new Deck(), new GameBoard());
    }

    @Test
    public void execute_drawFromEmptyRedDiscard_shouldThrowException() throws CommandException {
        player.addToHand(blue5Card);
        var deck = makeDeckFromCards(yellow4Card);
        var board = new GameBoard();
        var command = Command.builder().player(player).playCard(blue5Card).drawDiscardColor(Color.RED).build();

        assertTrue(board.getDiscardStack(Color.RED).isEmpty());

        thrown.expect(CommandException.class);

        command.execute(deck, board);
    }

    @Test
    public void execute_playCardNotInHandHand_shouldThrowException() throws CommandException {
        var command = Command.builder().player(player).playCard(blue5Card).build();

        thrown.expect(CardNotInHandCommandException.class);
        command.execute(makeDeckFromCards(yellow4Card), new GameBoard());
    }

    @Test
    public void execute_discardCardNotInHandHand_shouldThrowException() throws CommandException {
        var command = Command.builder().player(player).discardCard(blue5Card).build();

        thrown.expect(CardNotInHandCommandException.class);
        command.execute(makeDeckFromCards(yellow4Card), new GameBoard());
    }

    @Test
    public void execute_playCardOfLowerValueThanTopCardForColor_shouldThrowException() throws CommandException {
        var green4Card = Card.createExpeditionCard(Color.GREEN, 4);
        var green5Card = Card.createExpeditionCard(Color.GREEN, 5);
        var deck = makeDeckFromCards(green2Card);
        var board = new GameBoard();

        board.addCardInPlay(player.getId(), green5Card); // Green5 card is in play
        player.addToHand(green4Card);

        // User may not play a card with lower value than a card already in play for the card's color
        // Thus, trying to play Green4 should fail because Green5 is in play
        var command = Command.builder().player(player).playCard(green4Card).build();

        thrown.expect(CannotPlayLowerValueCardCommandException.class);

        command.execute(deck, board);
    }

    @Test
    public void execute_playCardAndDrawFromDeck_shouldAddCardToCorrectCardPileAndRemoveCardFromDeck() throws CommandException {
        var deck = makeDeckFromCards(green2Card);
        var board = new GameBoard();
        player.addToHand(blue5Card);

        var command = Command.builder().player(player).playCard(blue5Card).build();
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

        var command = Command.builder().player(player).playCard(blue5Card).drawDiscardColor(Color.YELLOW).build();
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

        var command = Command.builder().player(player).discardCard(blue5Card).build();
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

        var command = Command.builder().player(player).discardCard(blue5Card).drawDiscardColor(Color.YELLOW).build();
        command.execute(deck, board);

        assertThat(deck.getCards(), contains(green2Card)); // did not draw from deck, so green 2 should still be there

        assertThat(board.getDiscardStack(Color.YELLOW), not(contains(yellow4Card)));
        assertThat(player.getHand(), contains(yellow4Card));

        assertThat(player.getHand(), not(contains(blue5Card)));
        assertThat(board.getDiscardStack(Color.BLUE), contains(blue5Card));
    }

    private Deck makeDeckFromCards(Card ...cards) {
        return Deck.fromList(Lists.newArrayList(cards));
    }
}
