package com.lostcities.lostcities.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lostcities.lostcities.domain.game.GameBoard;
import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.CardStack;
import com.lostcities.lostcities.domain.game.card.Color;
import java.util.HashMap;
import java.util.Map;

public class GameBoardDto {

    @JsonProperty("discard")
    public Map<Color, DiscardSummary> topOfDiscards;

    @JsonProperty("inPlay")
    public Map<Long, Map<Color, CardStack>> inPlayCardStacks;

    private GameBoardDto(Map<Color, DiscardSummary> topOfDiscards, Map<Long, Map<Color, CardStack>> inPlayCardStacks) {
        this.topOfDiscards = topOfDiscards;
        this.inPlayCardStacks = inPlayCardStacks;
    }

    public static GameBoardDto fromGameBoard(GameBoard gameBoard) {
        Map<Color, DiscardSummary> topOfDiscards = new HashMap<>();
        for(var entry : gameBoard.getDiscardStacksMap().entrySet()) {
            Color color = entry.getKey();
            CardStack cardStack = entry.getValue();
            cardStack.getTop().ifPresent(topCard -> {
                topOfDiscards.put(color, new DiscardSummary(cardStack.size(), topCard));
            });
        }
        return new GameBoardDto(topOfDiscards, gameBoard.getInPlayCardStacks());
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
