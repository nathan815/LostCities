package com.lostcities.lostcities.game;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.HashSet;
import java.util.Set;

public class Player {

    private Long playerId;
    private Game game;

    private Set<Card> hand;

    private Multimap<Color, Card> inPlay;

    public Player(Long playerId, Game game) {
        this.playerId = playerId;
        this.game = game;

        inPlay = ArrayListMultimap.create();
        hand = new HashSet<>();
    }

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
        hand.remove(card);
    }

    protected void discard(Card card) {
        //TODO: Fix problem discarding a single multiplier with multiple multipliers in hand.
        hand.remove(card);
        game.discard(card);
    }

    protected void draw() {
        this.hand.add(game.draw());
    }

    protected void drawFromDiscard(Color color) {
        //TODO: implement
    }
}
