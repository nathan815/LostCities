package com.lostcities.lostcities.application.service;

import com.lostcities.lostcities.application.dto.CommandDto;
import com.lostcities.lostcities.application.dto.GameDto;
import com.lostcities.lostcities.domain.game.Command;
import com.lostcities.lostcities.domain.game.CommandException;
import com.lostcities.lostcities.domain.game.CommandRepository;
import com.lostcities.lostcities.domain.game.Game;
import com.lostcities.lostcities.domain.game.GameRepository;
import com.lostcities.lostcities.domain.game.Player;
import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.user.User;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private Random seedGenerator = new Random();
    private GameRepository gameRepository;
    private CommandRepository commandRepository;

    public GameService(GameRepository gameRepository, CommandRepository commandRepository) {
        this.gameRepository = gameRepository;
        this.commandRepository = commandRepository;
    }

    public GameDto createGame(User user) {
        Player player = new Player(user.getId(), user.getUsername());
        Game game = Game.create(player, seedGenerator.nextLong());
        gameRepository.save(game);
        return GameDto.fromGame(game);
    }

    public GameDto joinGame(long gameId, User user) {
        Player player = new Player(user.getId(), user.getUsername());
        Game game = getGame(gameId);
        game.joinGameAsSecondPlayer(player);
        gameRepository.save(game);
        return GameDto.fromGame(game);
    }

    public GameDto makeMove(long gameId, User user, CommandDto commandDto) throws CommandException {
        Game game = getGame(gameId);
        Player player = game.getPlayerById(user.getId())
                .orElseThrow(() -> new RuntimeException("Invalid player for this game!"));
        var command = Command.builder()
                .player(player)
                .playCard(Card.fromString(commandDto.getPlay()))
                .discardCard(Card.fromString(commandDto.getDiscard()))
                .drawDiscardColor(commandDto.getDraw())
                .build();
        game.runCommand(command);
        commandRepository.save(game.getId(), command);
        gameRepository.save(game);
        return GameDto.fromGame(game);
    }

    private Game getGame(long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game does not exist"));
    }

}
