package com.lostcities.lostcities.persistence.command;

import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface CommandEntityDao extends CrudRepository<CommandEntity, Long> {

    List<CommandEntity> findAllByGameId(long gameId);
}
