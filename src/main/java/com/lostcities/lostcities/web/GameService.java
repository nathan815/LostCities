package com.lostcities.lostcities.web;

import com.lostcities.lostcities.entity.GameEntity;
import com.lostcities.lostcities.entity.PlayerEntity;
import com.lostcities.lostcities.repository.GameRepository;
import com.lostcities.lostcities.repository.PlayerRepository;
import com.lostcities.lostcities.web.model.CommandDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping("/api/games")
public class GameService {

    private GameRepository gameRepository;
    private PlayerRepository playerRepository;

    public GameService(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @RequestMapping("/")
    public Collection<GameEntity> getGames() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return gameRepository.getGameEntitiesByPlayer1UserUsernameOrPlayer2UserUsername(user.getUsername());
    }

    @RequestMapping("/{id}")
    public GameEntity getGameEntity(@PathVariable Long id) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setId(id);
        return gameEntity;
    }

    @RequestMapping(method=RequestMethod.POST)
    public GameEntity createGame() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PlayerEntity playerEntity = playerRepository.getPlayerEntityByUserUsername(user.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        GameEntity gameEntity = GameEntity.createGame(playerEntity);

        return gameRepository.save(gameEntity);
    }

    @RequestMapping(value="/{gameId}", method=RequestMethod.PATCH)
    public GameEntity joinGame(@PathVariable Long gameId, @RequestParam Long playerId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        PlayerEntity playerEntity = playerRepository.getPlayerEntityByUserUsername(user.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        gameEntity.setPlayer2(playerEntity);

        return gameRepository.save(gameEntity);
    }

    @RequestMapping(value="/{gameId}", method=RequestMethod.POST)
    public GameEntity exececuteCommand(@RequestBody CommandDto commandDto) {

        return null;
    }
}
