package com.lostcities.lostcities.domain.model.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.lostcities.lostcities.persistence.entity.GameEntity;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;


public class Game {
    private Long gameId;

    @JsonProperty
    private Player player1;

    @JsonProperty
    private Player player2;

    @JsonProperty
    private CardDeck deck;

    private GameBoard board;

    public Game(CardDeck deck, GameBoard board, Player player1, Player player2) {
        player1.setOpponent(player2);
        player2.setOpponent(player1);
        this.deck = deck;
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        drawStartingHands();
    }

    public Long getId() {
        return gameId;
    }

    public CardDeck getDeck() {
        return deck;
    }

    public Optional<Player> getPlayerById(Long id) {
        return Stream.of(player1, player2)
                .filter(player -> player.getPlayerId().equals(id))
                .findFirst();
    }

    private void drawStartingHands() {
        for(int i = 0; i < 8; i++) {
            deck.draw().ifPresent(card -> player1.addToHand(card));
            deck.draw().ifPresent(card -> player2.addToHand(card));
        }
    }

    public void runCommands(LinkedHashSet<Command> commands) {
        for(Command command : commands) {
            runCommand(command);
        }
    }

    public void runCommand(Command command) {
        command.execute(deck, board);
    }

    public static Game fromGameEntity(GameEntity gameEntity) {
        CardDeck deck = CardDeck.getShuffledDeck(new Random(gameEntity.getSeed()));
        Player player1 = new Player(
                gameEntity.getPlayer1().getId(),
                gameEntity.getPlayer1().getName());

        Player player2 = new Player(
                gameEntity.getPlayer2().getId(),
                gameEntity.getPlayer2().getName());
        return new Game(deck, new GameBoard(), player1, player2);
    }
}
