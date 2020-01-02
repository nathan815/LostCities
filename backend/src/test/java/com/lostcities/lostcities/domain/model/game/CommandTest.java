package com.lostcities.lostcities.domain.model.game;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class CommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    Player mockPlayer;

    @Before
    public void setup() {
        mockPlayer = Mockito.mock(Player.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_bothPlayAndDiscardCard_shouldThrowException() {
        new Command(mockPlayer,
                Card.createExpeditionCard(Color.RED, 3),
                Card.createExpeditionCard(Color.GREEN, 4),
                Color.BLUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_noPlayOrDiscardCard_shouldThrowException() {
        new Command(mockPlayer, null, null, Color.BLUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_drawSameColorJustDiscarded_shouldThrowException() {
        new Command(mockPlayer, null, Card.createExpeditionCard(Color.BLUE, 5), Color.BLUE);
    }

    @Test
    public void execute_emptyDeck_shouldThrowException() {
        var command = new Command(mockPlayer, Card.createExpeditionCard(Color.BLUE, 5), null, Color.RED);

        thrown.expect(RuntimeException.class);
        command.execute(new Deck(), new GameBoard());
    }
}
