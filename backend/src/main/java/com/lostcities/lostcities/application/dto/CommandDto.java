package com.lostcities.lostcities.application.dto;

import com.lostcities.lostcities.domain.model.game.Color;

public class CommandDto {
    private Long gameId;
    private Long playerId;
    private String play;
    private String discard;
    private Color draw;

    public CommandDto() {

    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
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
