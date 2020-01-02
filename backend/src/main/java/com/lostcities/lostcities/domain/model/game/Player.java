package com.lostcities.lostcities.domain.model.game;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Player {

    private Long playerId;

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

    protected void addToHand(Card card) {
        hand.add(card);
    }

    protected void drawFromDeck(CardDeck deck) {
        deck.draw().ifPresent(card -> hand.add(card));
    }

    protected void removeFromHand(Card card) {
        hand.remove(card);
    }

    protected void drawFromDiscard(Color color) {
        //TODO: implement
    }


}
