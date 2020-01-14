package com.lostcities.lostcities.web;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GameWebsocketController
{
    Logger logger = LoggerFactory.getLogger(GameWebsocketController.class);

    @MessageMapping("/upper")
    @SendTo("/topic/test")
    public HashMap<String, String> test(HashMap<String, String> data) {
        var resp = new HashMap<String, String>();
        resp.put("upper", data.get("msg").toUpperCase());
        logger.info("Test: " + resp);
        return resp;
    }

    @SubscribeMapping("/game/{id}")
    public HashMap<String, Integer> gameTest(@DestinationVariable int id) {
        var resp = new HashMap<String, Integer>();
        resp.put("id", id);
        logger.info("Game Subscribe: " + resp);
        return resp;
    }
}
