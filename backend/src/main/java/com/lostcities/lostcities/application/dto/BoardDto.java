package com.lostcities.lostcities.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lostcities.lostcities.domain.game.GameBoard;
import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.CardStack;
import com.lostcities.lostcities.domain.game.card.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardDto {

    private static final int DISCARD_COUNT = 3;

    @JsonProperty("discard")
    public Map<Color, List<Card>> topOfDiscards;

    private BoardDto(Map<Color, List<Card>> topOfDiscards) {
        this.topOfDiscards = topOfDiscards;
    }

    public static BoardDto fromGameBoard(GameBoard gameBoard) {
        Map<Color, List<Card>> topOfDiscards = new HashMap<>();
        for (var entry : gameBoard.getDiscardStacksMap().entrySet()) {
            Color color = entry.getKey();
            CardStack cardStack = entry.getValue();
            topOfDiscards.put(color, cardStack.getTopCards(DISCARD_COUNT));
        }
        return new BoardDto(topOfDiscards);
    }

}
