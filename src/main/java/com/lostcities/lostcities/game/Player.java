package com.lostcities.lostcities.game;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Set;

public class Player {

    private Long playerId;
    private Game game;

    private Set<Card> hand;

    private Multimap<Color, Card> inPlay = ArrayListMultimap.create();

    public Long getPlayerId() {
        return playerId;
    }

    public Integer score() {
        //TODO: implement
        return null;
    }

    public Set<Card> getHand() {
        return hand;
    }

    public Multimap<Color, Card> getInPlay() {
        return inPlay;
    }

    protected void play(Card card) {
        //TODO: implement
    }

    protected void discard(Card card) {
        //TODO: implement
    }

    protected void draw() {
        //TODO: implement
    }

    protected void drawFromDiscard(Color color) {
        //TODO: implement
    }
}
