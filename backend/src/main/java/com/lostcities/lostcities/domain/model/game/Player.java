package com.lostcities.lostcities.domain.model.game;

import com.lostcities.lostcities.domain.model.game.card.Card;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Player {

    private long id;
    private String name;
    private Set<Card> hand;

    public Player(long id, String name) {
        this.id = id;
        this.name = name;
        this.hand = new LinkedHashSet<>();
    }

    public long getId() {
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

    protected boolean removeFromHand(Card card) {
        return hand.remove(card);
    }

    protected boolean hasCard(Card card) {
        return hand.contains(card);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
