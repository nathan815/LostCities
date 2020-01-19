package com.lostcities.lostcities.persistence.move;

import com.lostcities.lostcities.domain.game.Move;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface MoveDao extends CrudRepository<Move, Long> {

    List<Move> findAllByGameId(long gameId);
}
