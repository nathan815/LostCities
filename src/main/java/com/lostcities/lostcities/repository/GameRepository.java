package com.lostcities.lostcities.repository;

import com.lostcities.lostcities.entity.GameEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GameRepository extends CrudRepository<GameEntity, Long> {

    @Query("SELECT gameEntity FROM GameEntity gameEntity " +
           "WHERE " +
            "(gameEntity.player1 is not null AND gameEntity.player1.name = :username) OR " +
            "(gameEntity.player2 is not null AND gameEntity.player2.name = :username)")
    public Collection<GameEntity> getGamesWithPlayer(String username);

    @Query("SELECT gameEntity FROM GameEntity gameEntity WHERE gameEntity.player2 is null")
    public Collection<GameEntity> getOpenGames();
}
