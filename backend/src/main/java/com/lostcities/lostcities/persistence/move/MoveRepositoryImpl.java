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

    private MoveEntityDao moveEntityDao;

    public MoveRepositoryImpl(MoveEntityDao moveEntityDao) {
        this.moveEntityDao = moveEntityDao;
    }

    @Override
    @CacheEvict(value="gameMoves", key="#gameId")
    public void save(long gameId, Move move) {
        MoveEntity moveEntity = new MoveEntity(gameId,
                move.getPlayer().getId(),
                move.getPlayCard().toString(),
                move.getDiscardCard().toString(),
                move.getDrawDiscardColor()
        );
        moveEntityDao.save(moveEntity);
    }

    @Override
    @Cacheable(value="gameMoves", key="#game.id")
    public List<Move> getMovesForGame(Game game) {
        return moveEntityDao.findAllByGameId(game.getId()).stream().map(moveEntity -> {
            var player = game.getPlayerById(moveEntity.getUserId()).get();
            var playCard = Card.fromString(moveEntity.getPlayCard());
            var discardCard = Card.fromString(moveEntity.getDiscardCard());

            return Move.builder().player(player).playCard(playCard).discardCard(discardCard)
                    .drawDiscardColor(moveEntity.getDrawDiscardCardColor()).build();
        }).collect(toList());
    }
}
