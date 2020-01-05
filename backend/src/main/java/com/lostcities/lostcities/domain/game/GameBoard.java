package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.CardStack;
import com.lostcities.lostcities.domain.game.card.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameBoard {

    // Cards discarded by color
    private Map<Color, CardStack> discardStacks;

    // Cards in play by color by each player
    private Map<Long, Map<Color, CardStack>> inPlayCardStacks;

    public GameBoard(Map<Color, CardStack> discard, Map<Long, Map<Color, CardStack>> inPlayCardStacks) {
        this.discardStacks = discard;
        this.inPlayCardStacks = inPlayCardStacks;
    }

    public GameBoard() {
        this(new HashMap<>(), new HashMap<>());
    }

    protected void addCardInPlay(long playerId, Card card) {
        getInPlayCardStack(card.getColor(), playerId).addToTop(card);
    }

    protected CardStack getInPlayCardStack(Color color, long playerId) {
        getInPlayCardStacks(playerId).putIfAbsent(color, new CardStack());
        return getInPlayCardStacks(playerId).get(color);
    }

    protected Map<Color, CardStack> getInPlayCardStacks(long playerId) {
        inPlayCardStacks.putIfAbsent(playerId, new HashMap<>());
        return inPlayCardStacks.get(playerId);
    }

    public Map<Long, Map<Color, CardStack>> getInPlayCardStacks() {
        return inPlayCardStacks;
    }

    protected Optional<Card> drawFromDiscard(Color color) {
        return getDiscardStack(color).removeTop();
    }

    protected void addToDiscard(Card card) {
        getDiscardStack(card.getColor()).addToTop(card);
    }

    protected CardStack getDiscardStack(Color color) {
        discardStacks.putIfAbsent(color, new CardStack());
        return discardStacks.get(color);
    }

    public Map<Color, CardStack> getDiscardStacksMap() {
        return discardStacks;
    }
}
