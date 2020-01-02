package com.lostcities.lostcities.domain.model.game;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty
    private Map<Color, CardDeck> discard = new HashMap<>();

    public Game(CardDeck deck, Player player1, Player player2) {
        player1.setOpponent(player2);
        player2.setOpponent(player1);
        this.deck = deck;
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

    public Map<Color, CardDeck> getDiscard() {
        return discard;
    }

    public Optional<Player> getPlayerById(Long id) {
        return Stream.of(player1, player2)
                .filter(player -> player.getPlayerId().equals(id))
                .findFirst();
    }

    private void drawStartingHands() {
        for(int i = 0; i < 8; i++) {
            player1.draw();
            player2.draw();
        }
    }

    public void runCommands(LinkedHashSet<Command> commands) {
        for(Command command : commands) {
            runCommand(command);
        }
    }

    public void runCommand(Command command) {
        command.execute();
    }

    Card draw() {
        Card card = deck.draw();
        return card;
    }

    public void discard(Card card) {
        discard.get(card.getColor()).add(card);
    }

    public static Game fromGameEntity(GameEntity gameEntity) {
        CardDeck deck = CardDeck.getShuffledDeck(new Random(gameEntity.getSeed()));
        Player player1 = new Player(
                gameEntity.getPlayer1().getId(),
                gameEntity.getPlayer1().getName());

        Player player2 = new Player(
                gameEntity.getPlayer2().getId(),
                gameEntity.getPlayer2().getName());
        return new Game(deck, player1, player2);
    }
}
