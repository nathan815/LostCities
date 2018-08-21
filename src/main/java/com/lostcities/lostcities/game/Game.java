package com.lostcities.lostcities.game;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;

public class Game {
    private Long gameId;

    private Player player1;
    private Player player2;

    private Collection<Card> deck;

    private Multimap<Color, Card> discard = ArrayListMultimap.create();


}
