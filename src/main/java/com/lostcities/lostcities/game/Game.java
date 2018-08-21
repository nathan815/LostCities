package com.lostcities.lostcities.game;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.lostcities.lostcities.entity.GameEntity;

import java.util.LinkedHashSet;
import java.util.Optional;

public class Game {
    private Long gameId;

    private Player player1;
    private Player player2;

    private LinkedHashSet<Card> deck;

    private Multimap<Color, Card> discard = ArrayListMultimap.create();

    public Game(LinkedHashSet<Card> deck) {
        this.deck = deck;
    }

    private void drawStartingHands() {
        player1.draw();
        player1.draw();
        player1.draw();
        player1.draw();
        player1.draw();
        player1.draw();
        player1.draw();
        player1.draw();

        player2.draw();
        player2.draw();
        player2.draw();
        player2.draw();
        player2.draw();
        player2.draw();
        player2.draw();
        player2.draw();
    }

    Card draw() {
        Card card = deck.stream().findFirst().get();
        deck.remove(card);

        return card;
    }


    Optional<Player> getPlayerById(Long id) {
        if(player1.getPlayerId().equals(id)) {
            return Optional.of(player1);
        } else if(player2.getPlayerId().equals(id)) {
            return Optional.of(player2);
        }

        return Optional.empty();
    }

    public static Game fromGameEntity(GameEntity gameEntity) {
        LinkedHashSet<Card> deck = Cards.getDeck(gameEntity.getSeed());
        Game game = new Game(deck);
        game.drawStartingHands();

        return game;
    }

    public void discard(Card card) {
        discard.put(card.color, card);
    }
}
