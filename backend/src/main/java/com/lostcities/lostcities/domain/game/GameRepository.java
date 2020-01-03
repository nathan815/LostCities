package com.lostcities.lostcities.domain.game;

import java.util.List;

public interface GameRepository {
    public Game findById(int id);

    public List<GameInfo> findGamesForPlayer(int id);
}
