package com.lostcities.lostcities.web;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
        return resp;
    }
}
