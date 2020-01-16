package com.lostcities.lostcities.web;

import com.lostcities.lostcities.application.dto.GameDto;
import com.lostcities.lostcities.application.dto.MoveDto;
import com.lostcities.lostcities.application.service.GameService;
import com.lostcities.lostcities.domain.game.MoveException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class GameWebsocketController {
    private static Logger logger = LoggerFactory.getLogger(GameWebsocketController.class);
    private GameService gameService;

    public GameWebsocketController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * When client subscribes to /app/game/{id}, this responds with game data immediately
     */
    @SubscribeMapping("/game/{id}")
    public GameDto getInitialGameData(@DestinationVariable int id) {
        var gameDto = gameService.getGame(id);
        logger.info("Game: " + gameDto);
        return gameDto;
    }

    @MessageMapping("/game/{id}/move")
    @SendTo("/topic/game/{id}")
    public GameDto gameCommand(@DestinationVariable long id, MoveDto move) throws MoveException {
        return gameService.makeMove(id, null, move);
    }

    @MessageExceptionHandler
    @SendToUser(value = "/queue/game/errors", broadcast = false)
    public Map<String, String> handleException(Throwable exception) {
        logger.error("Error in game WS controller", exception);
        var response = new HashMap<String, String>();
        response.put("error", exception.getMessage());
        return response;
    }

    private long parseGameId(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            logger.error("Non-number passed as game ID" + id, e);
            throw new RuntimeException("Game ID must be a number, but a non-number was provided: " + id, e);
        }
    }
}
