package com.lostcities.lostcities.web;

import com.lostcities.lostcities.entity.GameEntity;
import com.lostcities.lostcities.entity.PlayerEntity;
import com.lostcities.lostcities.game.Command;
import com.lostcities.lostcities.game.Game;
import com.lostcities.lostcities.repository.GameRepository;
import com.lostcities.lostcities.repository.PlayerRepository;
import com.lostcities.lostcities.web.dto.CommandDto;
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

    @GetMapping
    public Collection<GameEntity> getGames() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return gameRepository.getGamesWithPlayer(user.getUsername());
    }

    @GetMapping("/{id}")
    public Game getGameCall(@PathVariable Long id) {
        return createGame(id);
    }


    @PostMapping
    public GameEntity createGame() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PlayerEntity playerEntity = getPlayerEntity(user);

        GameEntity gameEntity = GameEntity.createGame(playerEntity);

        return gameRepository.save(gameEntity);
    }

    @PatchMapping("/{gameId}")
    public GameEntity joinGame(@PathVariable Long gameId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        GameEntity gameEntity = getGameEntity(gameId);

        PlayerEntity playerEntity = getPlayerEntity(user);

        gameEntity.setPlayer2(playerEntity);

        return gameRepository.save(gameEntity);
    }

    @PostMapping("/{gameId}")
    public GameEntity exececuteCommand(@RequestBody CommandDto commandDto) {
        Game game = createGame(commandDto.getGameId());

        Command command = new Command();



        return null;
    }

    private Game createGame(Long id) {
        GameEntity gameEntity = getGameEntity(id);
        return Game.fromGameEntity(gameEntity);
    }

    private GameEntity getGameEntity(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private PlayerEntity getPlayerEntity(User user) {
        return playerRepository.getPlayerEntityByUserUsername(user.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
