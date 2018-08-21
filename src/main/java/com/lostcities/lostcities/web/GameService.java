package com.lostcities.lostcities.web;

import com.lostcities.lostcities.entity.GameEntity;
import com.lostcities.lostcities.entity.PlayerEntity;
import com.lostcities.lostcities.repository.GameRepository;
import com.lostcities.lostcities.repository.PlayerRepository;
import com.lostcities.lostcities.web.model.CommandDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/games")
public class GameService {

    private GameRepository gameRepository;
    private PlayerRepository playerRepository;

    public GameService(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @RequestMapping("/{id}")
    public GameEntity getGameEntity(@PathVariable Long id) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setId(id);
        return gameEntity;
    }

    @RequestMapping(method=RequestMethod.POST)
    public GameEntity createGame(@RequestParam Long playerId) {
        PlayerEntity player1 = playerRepository.findById(playerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        GameEntity gameEntity = GameEntity.createGame(player1);

        return gameRepository.save(gameEntity);
    }

    @RequestMapping(value="/{gameId}", method=RequestMethod.PATCH)
    public GameEntity joinGame(@PathVariable Long gameId, @RequestParam Long playerId) {
        GameEntity gameEntity = gameRepository.findById(gameId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        PlayerEntity playerEntity = playerRepository.findById(playerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        gameEntity.setPlayer2(playerEntity);
        return gameRepository.save(gameEntity);
    }

    @RequestMapping(value="/{gameId}", method=RequestMethod.POST)
    public GameEntity exececuteCommand(CommandDto commandDto) {
        return null;
    }
}
