package com.lostcities.lostcities.web.websocket;

import com.lostcities.lostcities.application.dto.GameDto;
import com.lostcities.lostcities.application.dto.MoveDto;
import com.lostcities.lostcities.application.service.GameService;
import com.lostcities.lostcities.domain.game.MoveException;
import com.lostcities.lostcities.domain.user.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import static com.lostcities.lostcities.web.websocket.WebSocketConfig.USER_HEADER;

@Controller
public class GameWebsocketController {
    private static Logger logger = LoggerFactory.getLogger(GameWebsocketController.class);
    private GameService gameService;

    public GameWebsocketController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Sends game state to user on /user/topic/game/{id} channel
     */
    @MessageMapping("/game/{id}/requestState")
    @SendToUser("/topic/game/{id}")
    public GameDto getInitialGameData(@DestinationVariable String id,
                                      @Header(USER_HEADER) Optional<User> optUser) {
        logger.info("getInitialGameData - user: {}", optUser.orElse(null));
        long gameId = parseGameId(id);
        GameDto gameDto = optUser
                .map(user -> gameService.getGame(gameId, user.getId()))
                .orElseGet(() -> gameService.getGame(gameId));
        logger.info("Requested state for game {}", id);
        return gameDto;
    }

    /**
     * Join game and broadcast state to the game's topic
     */
    @MessageMapping("/game/{id}/join")
    @SendTo("/topic/game/{id}")
    public GameDto join(@DestinationVariable String id, @Header(USER_HEADER) Optional<User> optUser) {
        User user = optUser.orElseThrow(() -> new IllegalStateException("Must be logged in to join a game"));
        logger.info("join - user: " + user);
        GameDto game = gameService.getGame(parseGameId(id), user.getId());
        return game;
    }


    /**
     * Execute a move and broadcast state to the game's topic
     */
    @MessageMapping("/game/{id}/move")
    @SendTo("/topic/game/{id}")
    public GameDto makeMove(@DestinationVariable long id, MoveDto move) throws MoveException {
        return gameService.makeMove(id, null, move);
    }


    /**
     * Handles any exceptions occurring here; sends the error message to the originating client
     */
    @MessageExceptionHandler
    @SendToUser(value = "/queue/game/errors", broadcast = false)
    public Map<String, String> handleException(Throwable exception) {
        logger.error("Error in game socket controller", exception);
        var response = new HashMap<String, String>();
        response.put("error", exception.getMessage());
        return response;
    }

    private long parseGameId(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            logger.error("Non-number game ID provided " + id, e);
            throw new IllegalArgumentException("Invalid game ID (must be a number)", e);
        }
    }
}
