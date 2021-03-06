package com.lostcities.lostcities.application.service;

import com.lostcities.lostcities.application.dto.GameDto;
import com.lostcities.lostcities.application.dto.MoveDto;
import com.lostcities.lostcities.domain.game.Game;
import com.lostcities.lostcities.domain.game.GameRepository;
import com.lostcities.lostcities.domain.game.Move;
import com.lostcities.lostcities.domain.game.Player;
import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.user.User;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private Logger logger = LoggerFactory.getLogger(GameService.class);

    private Random seedGenerator = new Random();
    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameDto createGame(User user) {
        Player player = new Player(user.getId(), user.getUsername());
        Game game = Game.create(seedGenerator.nextLong(), player);
        gameRepository.save(game);
        return GameDto.fromGame(game);
    }

    public GameDto joinGame(long gameId, User user) {
        Player player = new Player(user.getId(), user.getUsername());
        Game game = getGameById(gameId);
        game.joinGameAsSecondPlayer(player);
        gameRepository.save(game);
        return GameDto.fromGame(game);
    }

    public GameDto makeMove(long gameId, User user, MoveDto moveDto) {
        Game game = getGameById(gameId);
        Player player = game.getPlayerById(user.getId())
                .orElseThrow(() -> new RuntimeException("Invalid player for this game!"));
        Move move = new Move(player, moveDto.getType(), Card.fromString(moveDto.getCard()), moveDto.getColor());
        move.setUser(user);
        game.makeMove(move);
        gameRepository.save(game);
        return GameDto.fromGame(game).withHand(player.getHand());
    }

    private Game getGameById(long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game does not exist"));
    }

    public GameDto getGame(long gameId, User user) {
        Game game = getGameById(gameId);
        gameRepository.save(game);
        Set<Card> hand = game.getPlayerById(user.getId()).map(Player::getHand).orElse(Collections.emptySet());
        return GameDto.fromGame(game).withHand(hand);
    }

    public GameDto getGame(long gameId) {
        Game game = getGameById(gameId);
        return GameDto.fromGame(game);
    }
}
