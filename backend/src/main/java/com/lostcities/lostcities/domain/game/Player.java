package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.CardStack;
import com.lostcities.lostcities.domain.game.card.Color;
import com.lostcities.lostcities.domain.game.card.InPlayCardStack;
import com.lostcities.lostcities.domain.game.exception.CardLowerValueException;
import com.lostcities.lostcities.domain.game.exception.CardNotInHandException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Player {

    public static final int HAND_SIZE = 8;

    private long id;
    private String name;
    private boolean readyToStart;
    private Set<Card> hand;
    private Map<Color, InPlayCardStack> inPlay;

    public Player(long id, String name) {
        this.id = id;
        this.name = name;
        this.hand = new LinkedHashSet<>();
        initializeInPlayMap();
    }

    private void initializeInPlayMap() {
        inPlay = new HashMap<>();
        for(var color : Color.values()) {
            inPlay.putIfAbsent(color, new InPlayCardStack());
        }
    }

    public long getId() {
        return id;
    }

    public boolean isReadyToStart() {
        return readyToStart;
    }

    public void setReadyToStart(boolean readyToStart) {
        this.readyToStart = readyToStart;
    }

    public Set<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public int calculateScore() {
        return inPlay.values().stream().map(InPlayCardStack::calculateValue).reduce(Integer::sum).orElse(0);
    }

    protected void addToHand(Card card) {
        hand.add(card);
    }

    protected void removeFromHand(Card card) {
        if(!hasCard(card)) {
            throw new CardNotInHandException(card);
        }
        hand.remove(card);
    }

    private boolean hasCard(Card card) {
        return hand.contains(card);
    }

    protected void play(Card card) {
        validatePlayCard(card);
        removeFromHand(card);
        getInPlay(card.getColor()).addToTop(card);
    }

    private void validatePlayCard(Card card) {
        getInPlay(card.getColor())
                .getTop()
                .filter(topCard -> card.getValue() < topCard.getValue())
                .ifPresent(topCard -> {
                    throw new CardLowerValueException(card, topCard);
                });
    }

    public CardStack getInPlay(Color color) {
        return inPlay.get(color);
    }

    public Map<Color, InPlayCardStack> getInPlay() {
        return inPlay;
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
