package com.lostcities.lostcities.application.dto;

import com.lostcities.lostcities.domain.game.Player;
import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerDto {
    private long id;
    private String name;
    private boolean readyToStart;
    private Map<Color, List<Card>> inPlay;

    public PlayerDto(long id, String name, boolean readyToStart, Map<Color, List<Card>> inPlay) {
        this.id = id;
        this.name = name;
        this.readyToStart = readyToStart;
        this.inPlay = inPlay;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isReadyToStart() {
        return readyToStart;
    }

    public Map<Color, List<Card>> getInPlay() {
        return inPlay;
    }

    public static PlayerDto fromPlayer(Player player) {
        Map<Color, List<Card>> inPlay = new HashMap<>();
        player.getInPlay().forEach((color, cardStack) -> inPlay.put(color, cardStack.getCards()));
        return new PlayerDto(player.getId(), player.getName(), player.isReadyToStart(), inPlay);
    }

}
