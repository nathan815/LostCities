package com.lostcities.lostcities.web;

import com.lostcities.lostcities.application.dto.GameDto;
import com.lostcities.lostcities.application.dto.MoveDto;
import com.lostcities.lostcities.application.service.GameService;
import com.lostcities.lostcities.domain.game.MoveException;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GameWebsocketController {
    Logger logger = LoggerFactory.getLogger(GameWebsocketController.class);

    GameService gameService;

    public GameWebsocketController(GameService gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("/upper")
    @SendTo("/topic/test")
    public HashMap<String, String> test(HashMap<String, String> data) {
        var resp = new HashMap<String, String>();
        resp.put("upper", data.get("msg").toUpperCase());
        logger.info("Test: " + resp);
        return resp;
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
}
