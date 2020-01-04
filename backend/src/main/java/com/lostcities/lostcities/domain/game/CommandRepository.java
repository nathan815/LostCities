package com.lostcities.lostcities.domain.game;


import java.util.List;

public interface CommandRepository {
    public void save(long gameId, Command command);

    public List<Command> getCommandsForGame(Game game);
}
