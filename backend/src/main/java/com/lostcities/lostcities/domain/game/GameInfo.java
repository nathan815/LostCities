package com.lostcities.lostcities.domain.game;

import java.util.List;

/**
 * Lightweight value object to hold information for a given Game
 */
public class GameInfo {
    private int id;
    private List<Integer> playerIds;

    public GameInfo(int id, List<Integer> playerIds) {
        this.id = id;
        this.playerIds = playerIds;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getPlayerIds() {
        return playerIds;
    }
}
