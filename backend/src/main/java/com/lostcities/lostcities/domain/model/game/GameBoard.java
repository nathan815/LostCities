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

    // Cards by color in play by each player
    private Map<Long, Multimap<Color, Card>> cardsPlayedByPlayer;

    public GameBoard(Multimap<Color, Card> discard, Map<Long, Multimap<Color, Card>> cardsPlayedByPlayer) {
        this.discardByColor = discard;
        this.cardsPlayedByPlayer = cardsPlayedByPlayer;
    }

    public GameBoard() {
        this(LinkedHashMultimap.create(), new HashMap<>());
    }

    protected void addPlayCard(Player player, Card card) {
        addPlayCard(player.getPlayerId(), card);
    }

    protected void addPlayCard(long playerId, Card card) {
        cardsPlayedByPlayer.get(playerId).put(card.getColor(), card);
    }

    protected Collection<Card> getColorCardsPlayedBy(Color color, long playerId) {
        return cardsPlayedByPlayer.get(playerId).get(color);
    }

    protected Multimap<Color, Card> getAllCardsPlayedBy(long playerId) {
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
