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

    public GameBoard(Map<Color, CardStack> discard) {
        this.discardStacks = discard;
    }

    public GameBoard() {
        this(new HashMap<>());
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
