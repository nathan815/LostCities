package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Deck;
import com.lostcities.lostcities.domain.user.User;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import javax.persistence.Cacheable;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

@Entity
@Cacheable
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Convert(converter = GameStatusEnumConverter.class)
    private Status status;

    private long randomSeed;

    /**
     * Player # turn - 1 or 2
     */
    @Transient
    private int playerTurn;

    @OneToOne
    @JoinColumn(name = "user_1_id")
    private User user1;

    @OneToOne
    @JoinColumn(name = "user_2_id")
    private User user2;

    @Transient
    private Player player1;

    @Transient
    private Player player2;

    @Transient
    private Deck deck;

    @Transient
    private GameBoard board;

    public Game() {
    }

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

    @PostLoad
    private void postLoadInit() {
        deck = Deck.getShuffledDeck(new Random(randomSeed));
        board = new GameBoard();
        if(user1 != null) {
            player1 = new Player(user1.getId(), user1.getUsername());
        }
        if(user2 != null) {
            player2 = new Player(user2.getId(), user2.getUsername());
        }
        restoreState();
    }

    @PrePersist
    private void beforePersisting() {
        if(user1 == null && player1 != null) {
            user1 = new User(player1.getId(), player1.getName());
        }
        if(this.user2 == null && this.player2 != null) {
            user2 = new User(player2.getId(), player2.getName());
        }
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

    public Status getStatus() {
        return status;
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

    public Stream<Player> getPlayersStream() {
        return Stream.of(player1, player2).filter(Objects::nonNull);
    }

    public Optional<Player> getPlayerById(Long playerId) {
        return getPlayersStream()
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
