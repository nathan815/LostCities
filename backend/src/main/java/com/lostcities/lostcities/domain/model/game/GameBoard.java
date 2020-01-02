package com.lostcities.lostcities.domain.model.game;

import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameBoard {

    // Cards discarded by color
    private Multimap<Color, Card> discardByColor;

    // Cards in play by color by each player id
    private Map<Long, Multimap<Color, Card>> cardsPlayedByPlayer;

    public GameBoard(Multimap<Color, Card> discard, Map<Long, Multimap<Color, Card>> cardsPlayedByPlayer) {
        this.discardByColor = discard;
        this.cardsPlayedByPlayer = cardsPlayedByPlayer;
    }

    public GameBoard() {
        this(LinkedHashMultimap.create(), new HashMap<>());
    }

    protected void addCardInPlay(long playerId, Card card) {
        getCardsPlayedBy(playerId).put(card.getColor(), card);
    }

    protected Collection<Card> getCardsOfColorPlayedBy(Color color, long playerId) {
        return getCardsPlayedBy(playerId).get(color);
    }

    protected Multimap<Color, Card> getCardsPlayedBy(long playerId) {
        if(!cardsPlayedByPlayer.containsKey(playerId)) {
            cardsPlayedByPlayer.put(playerId, LinkedHashMultimap.create());
        }
        return cardsPlayedByPlayer.get(playerId);
    }

    protected Optional<Card> drawFromDiscard(Color color) {
        var cardOpt = Optional.ofNullable(Iterables.getLast(discardByColor.get(color), null));
        cardOpt.ifPresent(card -> discardByColor.remove(color, card));
        return cardOpt;
    }

    protected Collection<Card> getDiscardForColor(Color color) {
        return discardByColor.get(color);
    }

    protected void addToDiscard(Card card) {
        discardByColor.put(card.getColor(), card);
    }
}
