package com.lostcities.lostcities.domain.game;

import java.util.List;

/**
 * Lightweight value object to hold information for a given Game
 */
public class GameInfo {
    private int id;
    private int player1;
    private int player2;

    public GameInfo(int id, int player1, int player2) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int getId() {
        return id;
    }

    public int getPlayer1() {
        return player1;
    }

    public int getPlayer2() {
        return player2;
    }
}
