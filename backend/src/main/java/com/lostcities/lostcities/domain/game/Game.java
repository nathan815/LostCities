package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Deck;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;


public class Game {

    private long id;
    private long randomSeed;

    private Player player1;
    private Player player2;
    private Deck deck;

    private GameBoard board;

    public Game(long id, long randomSeed, Deck deck, GameBoard board, Player player1, Player player2) {
        this.id = id;
        this.deck = deck;
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        if(player1 != null && player2 != null) {
            drawStartingHands();
        }
    }

    public long getId() {
        return id;
    }

    public Deck getDeck() {
        return deck;
    }

    public void joinGameAsSecondPlayer(Player player2) {
        this.player2 = player2;
        drawStartingHands();
    }

    public GameBoard getBoard() {
        return board;
    }

    public long getRandomSeed() {
        return randomSeed;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Optional<Player> getPlayerById(Long playerId) {
        return Stream.of(player1, player2)
                .filter(player -> player.getId() == playerId)
                .findFirst();
    }

    public void drawStartingHands() {
        for(int i = 0; i < 8; i++) {
            deck.draw().ifPresent(card -> player1.addToHand(card));
            deck.draw().ifPresent(card -> player2.addToHand(card));
        }
    }

    public void runCommands(List<Command> commands) throws CommandException {
        for(Command command : commands) {
            runCommand(command);
        }
    }

    public void runCommand(Command command) throws CommandException {
        command.execute(deck, board);
    }

    /**
     * Create instance of existing Game with two players joined
     */
    public static Game create(long id, long randomSeed, Player player1, Player player2) {
        Deck deck = Deck.getShuffledDeck(new Random(randomSeed));
        return new Game(id, randomSeed, deck, new GameBoard(), player1, player2);
    }

    /**
     * Create the initial Game instance with only one starting player
     *
     * @param player1    The starting user
     * @param randomSeed Seed for shuffling deck
     * @return Game
     */
    public static Game create(Player player1, long randomSeed) {
        return create(0, randomSeed, player1, null);
    }
}
