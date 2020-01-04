package com.lostcities.lostcities.persistence.repository;

import com.lostcities.lostcities.domain.game.CommandException;
import com.lostcities.lostcities.domain.game.CommandRepository;
import com.lostcities.lostcities.domain.game.Game;
import com.lostcities.lostcities.domain.game.GameInfo;
import com.lostcities.lostcities.domain.game.GameRepository;
import com.lostcities.lostcities.domain.game.Player;
import com.lostcities.lostcities.persistence.entity.GameEntity;
import com.lostcities.lostcities.persistence.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepositoryImpl implements GameRepository {

    private GameEntityDao gameEntityDao;
    private CommandRepository commandRepository;

    public GameRepositoryImpl(GameEntityDao gameEntityDao, CommandRepository commandRepository) {
        this.gameEntityDao = gameEntityDao;
        this.commandRepository = commandRepository;
    }

    public Game createNewGame(UserEntity user, long seed) {
        var entity = new GameEntity();
        entity.setUser1(user);
        entity.setSeed(seed);
        gameEntityDao.save(entity);
        return Game.create(new Player(user.getId(), user.getUsername()), seed);
    }

    @Override
    public Optional<Game> findById(long id) {
        return gameEntityDao.findById(id).map(gameEntity -> {
            var player1 = new Player(gameEntity.getUser1().getId(), gameEntity.getUser1().getUsername());
            var player2 = new Player(gameEntity.getUser2().getId(), gameEntity.getUser2().getUsername());
            var game = Game.create(gameEntity.getId(), gameEntity.getSeed(), player1, player2);
            var commands = commandRepository.getCommandsForGame(game);
            try {
                game.runCommands(commands);
            } catch(CommandException e) {
                // Commands from DB should be valid. Just log an error here if one is invalid.
                // TODO log error
            }
            return game;
        });
    }

    @Override
    public void save(Game game) {
        GameEntity gameEntity = new GameEntity();
        gameEntityDao.save(gameEntity);
    }

    @Override
    public List<GameInfo> findGamesForPlayer(int id) {
        return null;
    }
}
