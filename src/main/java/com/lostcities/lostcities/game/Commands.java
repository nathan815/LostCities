package com.lostcities.lostcities.game;

import java.util.LinkedHashSet;

public class Commands {

    public static void executeCommands(Game game, LinkedHashSet<Command> commands) {
        for(Command command : commands) {
            command.execute();
        }
    }
}
