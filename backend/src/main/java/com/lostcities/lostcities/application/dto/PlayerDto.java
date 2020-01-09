package com.lostcities.lostcities.application.dto;

import com.lostcities.lostcities.domain.game.Player;
import com.lostcities.lostcities.domain.game.card.CardStack;
import com.lostcities.lostcities.domain.game.card.Color;
import java.util.Map;

public class PlayerDto {
    private long id;
    private String name;
    private Map<Color, CardStack> inPlay;

    public PlayerDto(long id, String name, Map<Color, CardStack> inPlay) {
        this.id = id;
        this.name = name;
        this.inPlay = inPlay;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<Color, CardStack> getInPlay() {
        return inPlay;
    }

    public static PlayerDto fromPlayer(Player player) {
        return new PlayerDto(player.getId(), player.getName(), player.getInPlay());
    }

}
