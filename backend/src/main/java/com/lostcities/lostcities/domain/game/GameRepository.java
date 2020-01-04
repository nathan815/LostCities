package com.lostcities.lostcities.domain.game;

import java.util.List;
import java.util.Optional;

public interface GameRepository {
    public Optional<Game> findById(long id);

    public void save(Game game);

    public List<GameInfo> findGamesForPlayer(int id);
}
