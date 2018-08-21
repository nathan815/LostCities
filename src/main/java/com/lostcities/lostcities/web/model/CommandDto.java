package com.lostcities.lostcities.web.model;

import com.lostcities.lostcities.game.Card;
import com.lostcities.lostcities.game.Color;

public class CommandDto {
    private Long playerId;
    private Card play;
    private Card discard;
    private Color draw;

    public CommandDto() {
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Card getPlay() {
        return play;
    }

    public void setPlay(Card play) {
        this.play = play;
    }

    public Card getDiscard() {
        return discard;
    }

    public void setDiscard(Card discard) {
        this.discard = discard;
    }

    public Color getDraw() {
        return draw;
    }

    public void setDraw(Color draw) {
        this.draw = draw;
    }
}
