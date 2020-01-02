package com.lostcities.lostcities.domain.model.game;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Player {

    private Long playerId;
    private Game game;

    private String name;

    private Player opponent;

    private Set<Card> hand;

    private Map<Color, CardDeck> inPlay;

    public Player(Long playerId, String name) {
        this.playerId = playerId;
        this.name = name;

        inPlay = new HashMap<>();
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
