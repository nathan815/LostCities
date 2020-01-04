package com.lostcities.lostcities.persistence.repository;

import com.lostcities.lostcities.persistence.entity.CommandEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface CommandEntityDao extends CrudRepository<CommandEntity, Long> {

    List<CommandEntity> findAllByGameId(long gameId);
}
