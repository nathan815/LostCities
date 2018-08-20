package com.lostcities.lostcities.web;

import com.lostcities.lostcities.entity.GameEntity;
import com.lostcities.lostcities.entity.PlayerEntity;
import com.lostcities.lostcities.repository.GameRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
public class GameService {

    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @RequestMapping("/{id}")
    public GameEntity getGameEntity(@PathVariable Long id) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setId(id);
        return gameEntity;
    }

    @RequestMapping(value="/",method=RequestMethod.POST)
    public GameEntity createGame() {
        PlayerEntity player1 = new PlayerEntity();
        PlayerEntity player2 = new PlayerEntity();
        GameEntity gameEntity = GameEntity.createGame(player1, player2);
        return gameEntity;
    }
}
