package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.Deck;
import com.lostcities.lostcities.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

@Entity
@Cacheable
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private long randomSeed;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Move> moves;

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
    private Player currentTurnPlayer;

    @Transient
    private Deck deck;

    @Transient
    private GameBoard board;

    public Game() {
    }

    public Game(long randomSeed, Deck deck, GameBoard board, Player player1, Player player2) {
        this.deck = deck;
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.randomSeed = randomSeed;
        this.status = Status.New;
        this.moves = new ArrayList<>();
        restoreState();
    }

    public void joinGameAsSecondPlayer(Player player2) {
        this.player2 = player2;
        status = Status.ReadyToStart;
    }

    private void start() {
        drawStartingHands();
        status = Status.Started;
        currentTurnPlayer = player1;
    }

    private void gameOver() {
        status = Status.Ended;
    }

    private void restoreState() {
        if(player2 != null) {
            joinGameAsSecondPlayer(player2);
        }
        if(didStart()) {
            start();
        }
        reRunMoves();
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

    protected void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Deck getDeck() {
        return deck;
    }

    public GameBoard getBoard() {
        return board;
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

    private boolean allPlayersReady() {
        return Stream.of(player1, player2)
                .allMatch(p -> p != null && p.isReadyToStart());
    }

    public Optional<Player> getPlayerById(Long playerId) {
        return getPlayersStream()
                .filter(player -> player.getId() == playerId)
                .findFirst();
    }

    private void drawStartingHands() {
        var player1Hand = new ArrayList<Card>();
        var player2Hand = new ArrayList<Card>();

        // First draw all the cards, then actually "give" them to the players.
        // This way, if the is deck too small (shouldn't be), we can fail and players aren't left with partial hands.
        for(int i = 0; i < Player.HAND_SIZE; i++) {
            player1Hand.add(deck.draw().orElseThrow(EmptyDeckException::new));
            player2Hand.add(deck.draw().orElseThrow(EmptyDeckException::new));
        }

        player1Hand.forEach(card -> player1.addToHand(card));
        player2Hand.forEach(card -> player2.addToHand(card));
    }

    public void makeMove(Move move) {
        move.setGame(this);
        Move lastMove = moves.size() > 0 ? moves.get(moves.size() - 1) : null;
        runMove(move, lastMove);
        if(!deck.isEmpty()) {
            moves.add(move);
        }
    }

    private void reRunMoves() {
        Move previousMove = null;
        for(Move move : moves) {
            move.setPlayer(getPlayerById(move.getUser().getId()).orElse(null));
            runMove(move, previousMove);
            previousMove = move;
        }
    }

    private void runMove(Move move, Move previousMove) {
        validateMove(move, previousMove);
        move.execute(deck, board);
        postMoveUpdateState(move);
    }

    private void validateMove(Move move, Move previousMove) {
        if(getPlayersStream().noneMatch(player -> player == move.getPlayer())) {
            throw new IllegalStateException("Move's player is not valid");
        }
        if(deck.isEmpty()) {
            throw new EmptyDeckException("Cannot make move because deck is empty");
        }
        if(status != Status.Started && !move.allowedBeforeGameStarts()) {
            throw new GameNotStartedException();
        }
        if(!move.canPlayAfter(previousMove)) {
            throw new IllegalMoveException("Move " + move.getType() + " cannot be played after previously played move " +
                    (previousMove == null ? "" : previousMove.getType()));
        }
        if(move.doesTurnMatter() && !currentTurnPlayer.equals(move.getPlayer())) {
            throw new NotPlayersTurnException(currentTurnPlayer.getName());
        }
    }

    private void postMoveUpdateState(Move move) {
        if(!didStart() && allPlayersReady()) {
            start();
        } else if(deck.isEmpty()) {
            gameOver();
        } else if(move.doesEndTurn()) {
            advancePlayerTurn();
        }
    }

    private void advancePlayerTurn() {
        if(currentTurnPlayer.equals(player1)) {
            currentTurnPlayer = player2;
        } else {
            currentTurnPlayer = player1;
        }
    }

    /**
     * Create instance of existing Game with two players joined
     */
    public static Game create(long randomSeed, Player player1, Player player2) {
        Deck deck = Deck.createShuffled(new Random(randomSeed));
        return new Game(randomSeed, deck, new GameBoard(), player1, player2);
    }

    /**
     * Create the initial Game instance with only one starting player
     *
     * @param randomSeed Seed for shuffling deck
     * @param player1    The starting user
     * @return Game
     */
    public static Game create(long randomSeed, Player player1) {
        return create(randomSeed, player1, null);
    }

    public enum Status {
        New,
        ReadyToStart,
        Started,
        Ended
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", randomSeed=" + randomSeed +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", deck=" + deck +
                ", board=" + board +
                ", status=" + status +
                '}';
    }

    @PostLoad
    private void postLoad() {
        deck = Deck.createShuffled(new Random(randomSeed));
        board = new GameBoard();
        player1 = new Player(user1.getId(), user1.getUsername());
        if(user2 != null) {
            player2 = new Player(user2.getId(), user2.getUsername());
        }
        restoreState();
    }

    @PrePersist
    @PreUpdate
    private void beforeSaving() {
        if(user1 == null) {
            user1 = new User(player1.getId(), player1.getName());
        }
        if(user2 == null) {
            user2 = new User(player2.getId(), player2.getName());
        }
    }
}
