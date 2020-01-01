package com.lostcities.lostcities.domain.model.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lostcities.lostcities.persistence.entity.GameEntity;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Random;


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

    public Optional<Player> getPlayerById(Long id) {
        if(player1.getPlayerId().equals(id)) {
            return Optional.of(player1);
        } else if(player2.getPlayerId().equals(id)) {
            return Optional.of(player2);
        }

        return Optional.empty();
    }

    private Game(CardDeck deck) {
        this.deck = deck;
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
        Card card = deck.getFirst().get();
        deck.remove(card);

        return card;
    }

    public void discard(Card card) {
        discard.get(card.getColor()).add(card);
    }

    public static Game fromGameEntity(GameEntity gameEntity) {
        CardDeck deck = CardDeck.getShuffledDeck(new Random(gameEntity.getSeed()));
        Game game = new Game(deck);

        game.player1 = new Player(
                gameEntity.getPlayer1().getId(),
                gameEntity.getPlayer1().getName(),
                game);

        game.player2 = new Player(
                gameEntity.getPlayer2().getId(),
                gameEntity.getPlayer2().getName(),
                game);

        game.player1.setOpponent(game.player2);
        game.player2.setOpponent(game.player1);

        game.drawStartingHands();

        return game;
    }
}
