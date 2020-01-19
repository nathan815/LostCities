package com.lostcities.lostcities.persistence.move;

import com.lostcities.lostcities.domain.game.Move;
import com.lostcities.lostcities.domain.game.MoveRepository;
import com.lostcities.lostcities.domain.game.Game;
import com.lostcities.lostcities.domain.game.card.Card;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import static java.util.stream.Collectors.toList;

@Repository
public class MoveRepositoryImpl implements MoveRepository {

    private MoveDao moveDao;

    public MoveRepositoryImpl(MoveDao moveDao) {
        this.moveDao = moveDao;
    }

    @Override
    public void save(long gameId, Move move) {
        moveDao.save(move);
    }

    @Override
    public List<Move> getMovesForGame(Game game) {
        return moveDao.findAllByGameId(game.getId());
    }
}
