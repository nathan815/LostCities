package com.lostcities.lostcities.persistence.game;

import com.lostcities.lostcities.domain.game.Game;
import com.lostcities.lostcities.domain.game.GameInfo;
import com.lostcities.lostcities.domain.game.GameRepository;
import com.lostcities.lostcities.domain.game.MoveRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepositoryImpl implements GameRepository {

    private GameDao gameDao;
    private MoveRepository moveRepository;

    public GameRepositoryImpl(GameDao gameDao, MoveRepository moveRepository) {
        this.gameDao = gameDao;
        this.moveRepository = moveRepository;
    }

    @Override
    public Optional<Game> findById(long id) {
        return gameDao.findById(id);
    }

    @Override
    public void save(Game game) {
        gameDao.save(game);
    }

    @Override
    public List<GameInfo> findGamesForPlayer(int id) {
        return null;
    }
}
