package com.lostcities.lostcities.application.dto;

import com.lostcities.lostcities.domain.game.card.Color;

public class CommandDto {
    private String play;
    private String discard;
    private Color draw;

    public CommandDto() {
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public String getDiscard() {
        return discard;
    }

    public void setDiscard(String discard) {
        this.discard = discard;
    }

    public Color getDraw() {
        return draw;
    }

    public void setDraw(Color draw) {
        this.draw = draw;
    }
}
