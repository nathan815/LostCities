package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Deck;
import com.lostcities.lostcities.persistence.entity.GameEntity;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;


public class Game {

    private long id;
    private Player player1;
    private Player player2;
    private Deck deck;
    private GameBoard board;

    public Game(long id, Deck deck, GameBoard board, Player player1, Player player2) {
        this.id = id;
        this.deck = deck;
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        drawStartingHands();
    }

    public Game(long id, Player player1, Player player2) {
        this(id, new Deck(), new GameBoard(), player1, player2);
    }

    public long getId() {
        return id;
    }

    public Deck getDeck() {
        return deck;
    }

    public Optional<Player> getPlayerById(Long playerId) {
        return Stream.of(player1, player2)
                .filter(player -> player.getId() == playerId)
                .findFirst();
    }

    private void drawStartingHands() {
        for(int i = 0; i < 8; i++) {
            deck.draw().ifPresent(card -> player1.addToHand(card));
            deck.draw().ifPresent(card -> player2.addToHand(card));
        }
    }

    public void runCommands(LinkedHashSet<Command> commands) throws CommandException {
        for(Command command : commands) {
            runCommand(command);
        }
    }

    public void runCommand(Command command) throws CommandException {
        command.execute(deck, board);
    }

    public static Game fromGameEntity(GameEntity gameEntity) {
        Deck deck = Deck.getShuffledDeck(new Random(gameEntity.getSeed()));
        Player player1 = new Player(
                gameEntity.getPlayer1().getId(),
                gameEntity.getPlayer1().getName());

        Player player2 = new Player(
                gameEntity.getPlayer2().getId(),
                gameEntity.getPlayer2().getName());
        return new Game(gameEntity.getId(), deck, new GameBoard(), player1, player2);
    }
}
