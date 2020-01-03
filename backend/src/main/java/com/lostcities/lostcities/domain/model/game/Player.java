package com.lostcities.lostcities.domain.model.game;

import java.util.LinkedHashSet;
import java.util.Set;

public class Player {

    private Long playerId;

    private String name;

    private Set<Card> hand;

    public Player(Long playerId, String name) {
        this.playerId = playerId;
        this.name = name;
        this.hand = new LinkedHashSet<>();
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Set<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    protected void addToHand(Card card) {
        hand.add(card);
    }

    protected void removeFromHand(Card card) {
        hand.remove(card);
    }

}
