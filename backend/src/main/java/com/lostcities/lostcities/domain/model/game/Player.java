package com.lostcities.lostcities.domain.model.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class Player {

    private Long playerId;
    private Game game;

    private String name;

    private Player opponent;

    private CardDeck hand;

    @JsonProperty
    private Map<Color, CardDeck> inPlay;

    public Player(Long playerId, String name, Game game) {
        this.playerId = playerId;
        this.name = name;
        this.game = game;

        inPlay = new HashMap<>();
        hand = new CardDeck();
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Integer score() {
        //TODO: implement
        return null;
    }

    public CardDeck getHand() {
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

    public Map<Color, CardDeck> getInPlay() {
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
