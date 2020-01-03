package com.lostcities.lostcities.domain.model.game;

import java.util.LinkedHashSet;
import java.util.Set;

public class Player {

    private Long id;
    private String name;
    private Set<Card> hand;

    public Player(Long id, String name) {
        this.id = id;
        this.name = name;
        this.hand = new LinkedHashSet<>();
    }

    public Long getId() {
        return id;
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
