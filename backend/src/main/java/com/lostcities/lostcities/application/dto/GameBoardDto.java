package com.lostcities.lostcities.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.lostcities.lostcities.domain.game.GameBoard;
import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.Color;
import java.util.HashMap;
import java.util.Map;

public class GameBoardDto {

    @JsonProperty("discard")
    public Map<Color, DiscardSummary> topOfDiscards;

    @JsonProperty("inPlay")
    public Map<Long, Multimap<Color, Card>> cardsPlayedByPlayer;

    private GameBoardDto(Map<Color, DiscardSummary> topOfDiscards, Map<Long, Multimap<Color, Card>> cardsPlayedByPlayer) {
        this.topOfDiscards = topOfDiscards;
        this.cardsPlayedByPlayer = cardsPlayedByPlayer;
    }

    public static GameBoardDto fromGameBoard(GameBoard gameBoard) {
        Map<Color, DiscardSummary> topOfDiscards = new HashMap<>();
        for(var entry : gameBoard.getDiscardByColor().asMap().entrySet()) {
            var color = entry.getKey();
            var cards = entry.getValue();
            if(!cards.isEmpty()) {
                topOfDiscards.put(color, new DiscardSummary(cards.size(), Iterables.getLast(cards)));
            }
        }
        return new GameBoardDto(topOfDiscards, gameBoard.getCardsPlayed());
    }

    public static class DiscardSummary {
        int count;
        Card topCard;
        public DiscardSummary(int count, Card topCard) {
            this.count = count;
            this.topCard = topCard;
        }
    }

}
