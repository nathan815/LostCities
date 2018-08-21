package com.lostcities.lostcities.repository;

import com.lostcities.lostcities.entity.GameEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GameRepository extends CrudRepository<GameEntity, Long> {

    public Collection<GameEntity> getGameEntitiesByPlayer1UserUsernameOrPlayer2UserUsername(String username);
    public Collection<GameEntity> getGameEntitiesByPlayer2IsNull();
}
