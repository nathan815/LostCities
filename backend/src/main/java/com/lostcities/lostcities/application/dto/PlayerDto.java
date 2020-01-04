package com.lostcities.lostcities.application.dto;

import com.lostcities.lostcities.domain.game.Player;

public class PlayerDto {
    private long id;
    private String name;

    public PlayerDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static PlayerDto fromPlayer(Player player) {
        return new PlayerDto(player.getId(), player.getName());
    }
}
