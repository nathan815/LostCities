package com.lostcities.lostcities.domain.game;


import java.util.List;

public interface MoveRepository {
    public void save(long gameId, Move move);

    public List<Move> getMovesForGame(Game game);
}
