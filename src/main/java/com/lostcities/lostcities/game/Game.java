package com.lostcities.lostcities.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.LinkedHashMultimap;
import com.lostcities.lostcities.entity.GameEntity;

import java.util.LinkedHashSet;
import java.util.Optional;


public class Game {
    private Long gameId;

    @JsonProperty
    private Player player1;

    @JsonProperty
    private Player player2;

    @JsonProperty
    private LinkedHashSet<Card> deck;

    @JsonProperty
    private LinkedHashMultimap<Color, Card> discard = LinkedHashMultimap.create();

    Optional<Player> getPlayerById(Long id) {
        if(player1.getPlayerId().equals(id)) {
            return Optional.of(player1);
        } else if(player2.getPlayerId().equals(id)) {
            return Optional.of(player2);
        }

        return Optional.empty();
    }

    private Game(LinkedHashSet<Card> deck) {
        this.deck = deck;
    }

    private void drawStartingHands() {
        for(int i=0; i < 8; i++) {
            player1.draw();
            player2.draw();
        }
    }

    public void runCommands(LinkedHashSet<Command> commands) {
        for(Command command : commands) {
            command.execute();
        }
    }

    Card draw() {
        Card card = deck.stream().findFirst().get();
        deck.remove(card);

        return card;
    }

    public void discard(Card card) {
        discard.put(card.getColor(), card);
    }

    public static Game fromGameEntity(GameEntity gameEntity) {
        LinkedHashSet<Card> deck = Cards.getShuffledDeck(gameEntity.getSeed());
        Game game = new Game(deck);

        game.player1 = new Player(
                gameEntity.getPlayer1().getId(),
                gameEntity.getPlayer1().getName(),
                game);

        game.player2 = new Player(
                gameEntity.getPlayer2().getId(),
                gameEntity.getPlayer2().getName(),
                game);

        game.drawStartingHands();

        return game;
    }
}
