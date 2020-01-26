package com.lostcities.lostcities.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lostcities.lostcities.domain.game.Move;
import com.lostcities.lostcities.domain.game.card.Color;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MoveDto {
    private long playerId;
    private Move.Type type;
    private String card;
    private Color color;

    public MoveDto() {
    }

    public MoveDto(long playerId, Move.Type type, String card, Color color) {
        this.playerId = playerId;
        this.type = type;
        this.card = card;
        this.color = color;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Move.Type getType() {
        return type;
    }

    public void setType(Move.Type type) {
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public static MoveDto fromMove(Move move) {
        return new MoveDto(
                move.getPlayer().getId(),
                move.getType(),
                move.getCard() == null ? null : move.getCard().toString(),
                move.getColor()
        );
    }
}
