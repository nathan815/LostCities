package com.lostcities.lostcities.persistence.game;

import com.lostcities.lostcities.domain.game.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GameDao extends CrudRepository<Game, Long> {

    @Query("SELECT game FROM Game as game " +
           "WHERE " +
            "(game.user1 is not null AND game.user1.username = :username) OR " +
            "(game.user1 is not null AND game.user1.username = :username)")
    public Collection<Game> getGamesWithPlayer(String username);

    @Query("SELECT game FROM Game as game WHERE game.user2 is null")
    public Collection<Game> getOpenGames();
}
