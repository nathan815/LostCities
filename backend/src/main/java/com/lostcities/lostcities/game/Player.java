package com.lostcities.lostcities.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import java.util.LinkedHashSet;
import java.util.Set;

public class Player {

    private Long playerId;
    private Game game;

    private String name;

    private Player opponent;

    private LinkedHashSet<Card> hand;

    @JsonProperty
    private LinkedHashMultimap<Color, Card> inPlay;

    public Player(Long playerId, String name, Game game) {
        this.playerId = playerId;
        this.name = name;
        this.game = game;

        inPlay = LinkedHashMultimap.create();
        hand = new LinkedHashSet<>();
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

    public String getName() {
        return name;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
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
