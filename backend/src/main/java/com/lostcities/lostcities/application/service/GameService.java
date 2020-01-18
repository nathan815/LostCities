package com.lostcities.lostcities.application.service;

import com.lostcities.lostcities.application.dto.GameDto;
import com.lostcities.lostcities.application.dto.MoveDto;
import com.lostcities.lostcities.domain.game.Game;
import com.lostcities.lostcities.domain.game.GameRepository;
import com.lostcities.lostcities.domain.game.Move;
import com.lostcities.lostcities.domain.game.MoveException;
import com.lostcities.lostcities.domain.game.MoveRepository;
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
    private MoveRepository moveRepository;

    public GameService(GameRepository gameRepository, MoveRepository moveRepository) {
        this.gameRepository = gameRepository;
        this.moveRepository = moveRepository;
    }

    public GameDto createGame(User user) {
        Player player = new Player(user.getId(), user.getUsername());
        Game game = Game.create(player, seedGenerator.nextLong());
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

    public GameDto makeMove(long gameId, User user, MoveDto moveDto) throws MoveException {
        Game game = getGameById(gameId);
        Player player = game.getPlayerById(user.getId())
                .orElseThrow(() -> new RuntimeException("Invalid player for this game!"));
        var move = Move.builder()
                .player(player)
                .playCard(Card.fromString(moveDto.getPlay()))
                .discardCard(Card.fromString(moveDto.getDiscard()))
                .drawDiscardColor(moveDto.getDraw())
                .build();
        game.makeMove(move);
        moveRepository.save(game.getId(), move);
        gameRepository.save(game);
        return GameDto.fromGame(game).withHand(player.getHand());
    }

    private Game getGameById(long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game does not exist"));
    }

    public GameDto getGame(long gameId, long userId) {
        Game game = getGameById(gameId);
        Set<Card> hand = game.getPlayerById(userId).map(Player::getHand).orElse(Collections.emptySet());
        return GameDto.fromGame(game).withHand(hand);
    }

    public GameDto getGame(long gameId) {
        Game game = getGameById(gameId);
        return GameDto.fromGame(game);
    }
}
