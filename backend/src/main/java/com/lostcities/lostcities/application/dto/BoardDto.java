package com.lostcities.lostcities.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lostcities.lostcities.domain.game.GameBoard;
import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.CardStack;
import com.lostcities.lostcities.domain.game.card.Color;
import java.util.HashMap;
import java.util.Map;

public class BoardDto {

    @JsonProperty("discard")
    public Map<Color, DiscardSummary> topOfDiscards;

    private BoardDto(Map<Color, DiscardSummary> topOfDiscards) {
        this.topOfDiscards = topOfDiscards;
    }

    public static BoardDto fromGameBoard(GameBoard gameBoard) {
        Map<Color, DiscardSummary> topOfDiscards = new HashMap<>();
        for(var entry : gameBoard.getDiscardStacksMap().entrySet()) {
            Color color = entry.getKey();
            CardStack cardStack = entry.getValue();
            cardStack.getTop().ifPresent(topCard -> {
                topOfDiscards.put(color, new DiscardSummary(cardStack.size(), topCard));
            });
        }
        return new BoardDto(topOfDiscards);
    }

    private static class DiscardSummary {
        int count;
        Card topCard;

        public DiscardSummary(int count, Card topCard) {
            this.count = count;
            this.topCard = topCard;
        }
    }

}
