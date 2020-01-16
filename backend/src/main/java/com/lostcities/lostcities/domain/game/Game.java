package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Deck;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;


public class Game {

    private long id;
    private long randomSeed;

    /**
     * Player # turn - 1 or 2
     */
    private int playerTurn;
    private Player player1;
    private Player player2;
    private Deck deck;

    private GameBoard board;

    private Status status;

    public Game(long id, long randomSeed, Status status, Deck deck, GameBoard board, Player player1, Player player2) {
        this.id = id;
        this.deck = deck;
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.randomSeed = randomSeed;
        this.status = status;
        restoreState();
    }

    public void joinGameAsSecondPlayer(Player player2) {
        this.player2 = player2;
    }

    public void start() {
        drawStartingHands();
        status = Status.Started;
        playerTurn = 0;
    }

    public void gameOver() {
        status = Status.Ended;
    }

    private void restoreState() {
        if(didStart()) {
            start();
        }
    }

    private boolean didStart() {
        return status == Status.Started || status == Status.Ended;
    }

    public long getId() {
        return id;
    }

    public Deck getDeck() {
        return deck;
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
        if(player1 == null || player2 == null) {
            throw new IllegalStateException("Cannot draw starting hands because player 1 or 2 is missing");
        }
        for(int i = 0; i < 8; i++) {
            deck.draw().ifPresent(card -> player1.addToHand(card));
            deck.draw().ifPresent(card -> player2.addToHand(card));
        }
    }

    public void runMoves(List<Move> moves) throws MoveException {
        for(Move move : moves) {
            runMove(move);
        }
    }

    public void runMove(Move move) throws MoveException {
        move.execute(deck, board);
        // If deck is now empty, game is over
        if(deck.isEmpty()) {
            gameOver();
        }
    }

    /**
     * Create instance of existing Game with two players joined
     */
    public static Game create(long id, long randomSeed, Status status, Player player1, Player player2) {
        Deck deck = Deck.getShuffledDeck(new Random(randomSeed));
        return new Game(id, randomSeed, status, deck, new GameBoard(), player1, player2);
    }

    /**
     * Create the initial Game instance with only one starting player
     *
     * @param player1    The starting user
     * @param randomSeed Seed for shuffling deck
     * @return Game
     */
    public static Game create(Player player1, long randomSeed) {
        return create(0, randomSeed, Status.New, player1, null);
    }

    public enum Status {
        New(0),
        ReadyToStart(1),
        Started(2),
        Ended(3);

        public int code;
        Status(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static Status fromCode(int code) {
            return Arrays.stream(Status.values())
                    .filter(status -> status.code == code)
                    .findAny()
                    .orElseThrow(IllegalArgumentException::new);
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", randomSeed=" + randomSeed +
                ", playerTurn=" + playerTurn +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", deck=" + deck +
                ", board=" + board +
                ", status=" + status +
                '}';
    }
}
