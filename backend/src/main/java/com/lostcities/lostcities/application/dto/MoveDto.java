package com.lostcities.lostcities.application.dto;

import com.lostcities.lostcities.domain.game.Move;
import com.lostcities.lostcities.domain.game.card.Color;

public class MoveDto {
    private Move.Type type;
    private String card;
    private Color color;

    public MoveDto() {
    }

    public String getCard() {
        return card;
    }

    public Move.Type getType() {
        return type;
    }

    public void setType(Move.Type type) {
        this.type = type;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
