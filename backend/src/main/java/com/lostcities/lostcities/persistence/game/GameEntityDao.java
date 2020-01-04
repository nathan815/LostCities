package com.lostcities.lostcities.persistence.game;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GameEntityDao extends CrudRepository<GameEntity, Long> {

    @Query("SELECT gameEntity FROM GameEntity gameEntity " +
           "WHERE " +
            "(gameEntity.user1 is not null AND gameEntity.user1.username = :username) OR " +
            "(gameEntity.user1 is not null AND gameEntity.user1.username = :username)")
    public Collection<GameEntity> getGamesWithPlayer(String username);

    @Query("SELECT gameEntity FROM GameEntity gameEntity WHERE gameEntity.user2 is null")
    public Collection<GameEntity> getOpenGames();
}
