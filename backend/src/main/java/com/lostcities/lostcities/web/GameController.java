package com.lostcities.lostcities.web;

import com.lostcities.lostcities.application.dto.GameDto;
import com.lostcities.lostcities.application.service.GameService;
import com.lostcities.lostcities.domain.game.GameInfo;
import com.lostcities.lostcities.domain.user.User;
import com.lostcities.lostcities.web.security.AuthUserDetails;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import static com.lostcities.lostcities.web.security.AuthUtils.getPrincipalFromAuthentication;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private Logger logger = LoggerFactory.getLogger(GameController.class);
    private GameService gameService;
    private SimpMessagingTemplate message;

    public GameController(GameService gameService, SimpMessagingTemplate message) {
        this.gameService = gameService;
        this.message = message;
    }

    @GetMapping
    public Collection<GameInfo> getUserGames(@AuthenticationPrincipal AuthUserDetails authUserDetails) {
        return null;
    }

    @GetMapping("/{id}")
    public GameDto getGame(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public GameDto createGame() {
        return null;
    }

    @PostMapping("/{gameId}/join")
    public void joinGame(@PathVariable Long gameId, Authentication auth) {
        User user = getPrincipalFromAuthentication(auth)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
        logger.info("User Join: game id = {}, user = {}", gameId, user);
        GameDto gameDto = gameService.joinGame(gameId, user);
        message.convertAndSend("/topic/game/" + gameId, gameDto);
    }

}
