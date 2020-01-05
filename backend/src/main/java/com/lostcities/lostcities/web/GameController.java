package com.lostcities.lostcities.web;

import com.lostcities.lostcities.application.dto.CommandDto;
import com.lostcities.lostcities.application.dto.GameDto;
import com.lostcities.lostcities.application.service.GameService;
import com.lostcities.lostcities.domain.game.CommandException;
import com.lostcities.lostcities.domain.game.GameInfo;
import com.lostcities.lostcities.domain.user.User;
import com.lostcities.lostcities.web.security.AuthUser;
import java.util.Collection;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public Collection<GameInfo> getUserGames(@AuthenticationPrincipal AuthUser authUser) {
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
    public GameDto joinGame(@PathVariable Long gameId) {
        return null;
    }

    @PostMapping("/{gameId}/moves")
    public GameDto makeMove(@RequestParam long gameId,
                            @RequestBody CommandDto commandDto,
                            @AuthenticationPrincipal AuthUser authUser) throws CommandException {
        return gameService.makeMove(gameId, new User(authUser.getId(), authUser.getUsername()), commandDto);
    }

}
