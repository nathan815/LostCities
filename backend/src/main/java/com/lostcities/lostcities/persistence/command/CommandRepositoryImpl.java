package com.lostcities.lostcities.persistence.command;

import com.lostcities.lostcities.domain.game.Command;
import com.lostcities.lostcities.domain.game.CommandRepository;
import com.lostcities.lostcities.domain.game.Game;
import com.lostcities.lostcities.domain.game.card.Card;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import static java.util.stream.Collectors.toList;

@Repository
public class CommandRepositoryImpl implements CommandRepository {

    private CommandEntityDao commandEntityDao;

    public CommandRepositoryImpl(CommandEntityDao commandEntityDao) {
        this.commandEntityDao = commandEntityDao;
    }

    @Override
    @CacheEvict(value="gameCommands", key="#gameId")
    public void save(long gameId, Command command) {
        CommandEntity commandEntity = new CommandEntity(gameId,
                command.getPlayer().getId(),
                command.getPlayCard().toString(),
                command.getDiscardCard().toString(),
                command.getDrawDiscardColor()
        );
        commandEntityDao.save(commandEntity);
    }

    @Override
    @Cacheable(value="gameCommands", key="#game.id")
    public List<Command> getCommandsForGame(Game game) {
        return commandEntityDao.findAllByGameId(game.getId()).stream().map(commandEntity -> {
            var player = game.getPlayerById(commandEntity.getUserId()).get();
            var playCard = Card.fromString(commandEntity.getPlayCard());
            var discardCard = Card.fromString(commandEntity.getDiscardCard());

            return Command.builder().player(player).playCard(playCard).discardCard(discardCard)
                    .drawDiscardCardColor(commandEntity.getDrawDiscardCardColor()).build();
        }).collect(toList());
    }
}
