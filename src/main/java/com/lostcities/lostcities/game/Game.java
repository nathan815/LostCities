package com.lostcities.lostcities.game;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Optional;

public class Game {
    private Long gameId;

    private Player player1;
    private Player player2;

    private Collection<Card> deck;

    private Multimap<Color, Card> discard = ArrayListMultimap.create();

    Optional<Player> getPlayerById(Long id) {
        if(player1.getPlayerId().equals(id)) {
            return Optional.of(player1);
        } else if(player2.getPlayerId().equals(id)) {
            return Optional.of(player2);
        }

        return Optional.empty();
    }
}
