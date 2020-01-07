package com.lostcities.lostcities.persistence.move;

import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface MoveEntityDao extends CrudRepository<MoveEntity, Long> {

    List<MoveEntity> findAllByGameId(long gameId);
}
