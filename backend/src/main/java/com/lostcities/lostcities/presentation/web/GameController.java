package com.lostcities.lostcities.presentation.web;

import com.lostcities.lostcities.domain.model.game.CommandException;
import com.lostcities.lostcities.persistence.entity.CommandEntity;
import com.lostcities.lostcities.persistence.entity.GameEntity;
import com.lostcities.lostcities.persistence.entity.PlayerEntity;
import com.lostcities.lostcities.domain.model.game.Card;
import com.lostcities.lostcities.domain.model.game.Command;
import com.lostcities.lostcities.domain.model.game.Game;
import com.lostcities.lostcities.domain.model.game.Player;
import com.lostcities.lostcities.persistence.repository.CommandRepository;
import com.lostcities.lostcities.persistence.repository.GameRepository;
import com.lostcities.lostcities.persistence.repository.PlayerRepository;
import com.lostcities.lostcities.application.dto.CommandDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private CommandRepository commandRepository;

    public GameController(
            GameRepository gameRepository,
            PlayerRepository playerRepository,
            CommandRepository commandRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.commandRepository = commandRepository;
    }

    @GetMapping
    public Collection<GameEntity> getGames() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return gameRepository.getGamesWithPlayer(auth.getName());
    }

    @GetMapping("/{id}")
    public Game getGame(@PathVariable Long id) {
        return createGame(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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

        GameEntity gameEntity = getGameEntity(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        PlayerEntity playerEntity = getPlayerEntity(user);

        gameEntity.setPlayer2(playerEntity);

        return gameRepository.save(gameEntity);
    }


    @PostMapping("/{gameId}")
    public Game executeCommand(@RequestBody CommandDto commandDto) throws CommandException {
        GameEntity gameEntity = getGameEntity(commandDto.getGameId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Game game = Game.fromGameEntity(gameEntity);

        Player player = game.getPlayerById(commandDto.getPlayerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        PlayerEntity playerEntity = gameEntity.getPlayerEntityById(commandDto.getPlayerId()).get();

        Command command = new Command(
                player,
                Card.fromString(commandDto.getPlay()),
                Card.fromString(commandDto.getDiscard()),
                commandDto.getDraw()
        );

        game.runCommand(command);

        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setPlay(commandDto.getPlay());
        commandEntity.setDiscard(commandDto.getDiscard());
        commandEntity.setDraw(commandDto.getDraw());
        commandEntity.setGame(gameEntity);
        commandEntity.setPlayer(playerEntity);


        commandRepository.save(commandEntity);

        return game;
    }

    private Optional<Game> createGame(Long id) {
        Optional<GameEntity> gameEntity = getGameEntity(id);
        return gameEntity.map(Game::fromGameEntity);

    }

    private Optional<GameEntity> getGameEntity(Long id) {
        return gameRepository.findById(id);
    }

    private PlayerEntity getPlayerEntity(User user) {
        return playerRepository.getPlayerEntityByUserUsername(user.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
