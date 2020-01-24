package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {

    private GameBoard board;

    @Before
    public void setUpBoard() {
        board = new GameBoard();
    }

    @Test
    public void drawFromDiscard_shouldRemoveAndReturnCardsInLastInFirstOutOrder() {
        List<Card> cards = Arrays.asList(
                Card.createExpeditionCard(Color.GREEN, 2),
                Card.createExpeditionCard(Color.GREEN, 4),
                Card.createExpeditionCard(Color.RED, 5),
                Card.createExpeditionCard(Color.RED, 10),
                Card.createExpeditionCard(Color.BLUE, 8),
                Card.createExpeditionCard(Color.BLUE, 4),
                Card.createWagerCard(Color.RED, 0)
        );
        cards.forEach(board::addToDiscard);

        // Order should be LIFO (stack of cards)

        assertEquals(4, board.drawFromDiscard(Color.BLUE).get().getValue());
        assertEquals(8, board.drawFromDiscard(Color.BLUE).get().getValue());

        assertTrue(board.drawFromDiscard(Color.RED).get().isWager());
        assertEquals(10, board.drawFromDiscard(Color.RED).get().getValue());
        assertEquals(5, board.drawFromDiscard(Color.RED).get().getValue());

        assertEquals(4, board.drawFromDiscard(Color.GREEN).get().getValue());
        assertEquals(2, board.drawFromDiscard(Color.GREEN).get().getValue());
    }

    @Test
    public void drawFromDiscard_shouldReturnEmptyOptionalForEmptyDiscard() {
        assertEquals(Optional.empty(), board.drawFromDiscard(Color.BLUE));
        assertEquals(Optional.empty(), board.drawFromDiscard(Color.RED));
        assertEquals(Optional.empty(), board.drawFromDiscard(Color.GREEN));
        assertEquals(Optional.empty(), board.drawFromDiscard(Color.YELLOW));
        assertEquals(Optional.empty(), board.drawFromDiscard(Color.WHITE));
    }

    @Test
    public void addToDiscard_shouldAddCardToCorrectDiscardStack() {
        board.addToDiscard(Card.createExpeditionCard(Color.RED, 2));
        board.addToDiscard(Card.createExpeditionCard(Color.GREEN, 2));
        board.addToDiscard(Card.createExpeditionCard(Color.BLUE, 3));
        board.addToDiscard(Card.createExpeditionCard(Color.RED, 5));

        assertAllDiscardCardsSameColor(board, Color.RED);
        assertAllDiscardCardsSameColor(board, Color.BLUE);
        assertAllDiscardCardsSameColor(board, Color.GREEN);
    }

    private void assertAllDiscardCardsSameColor(GameBoard board, Color color) {
        for(Card card : board.getDiscardStack(color)) {
            assertEquals(color, card.getColor());
        }
    }
}
