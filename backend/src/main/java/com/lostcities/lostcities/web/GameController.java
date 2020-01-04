package com.lostcities.lostcities.web;

import com.lostcities.lostcities.application.dto.CommandDto;
import com.lostcities.lostcities.application.dto.GameDto;
import com.lostcities.lostcities.application.service.GameService;
import com.lostcities.lostcities.domain.game.CommandException;
import com.lostcities.lostcities.domain.game.GameInfo;
import com.lostcities.lostcities.web.security.AuthUserDetails;
import java.util.Collection;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
    public Collection<GameInfo> getUserGames(@AuthenticationPrincipal AuthUserDetails authUser) {
        return null;
    }

    @GetMapping("/{id}")
    public GameDto getGame(@PathVariable Long id) {
        //return createGame(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return null;
    }


    @PostMapping
    public GameDto createGame() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        PlayerEntity playerEntity = getPlayerEntity(user);
//
//        GameEntity gameEntity = GameEntity.createGame(playerEntity);
//
//        return gameRepository.save(gameEntity);
        return null;
    }

    @PatchMapping("/{gameId}/join")
    public GameDto joinGame(@PathVariable Long gameId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        GameEntity gameEntity = getGameEntity(gameId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//
//        PlayerEntity playerEntity = getPlayerEntity(user);
//
//        gameEntity.setPlayer2(playerEntity);
//
//        return gameRepository.save(gameEntity);
        return null;
    }

    @PostMapping("/{gameId}/moves")
    public GameDto makeMove(@RequestParam long gameId,
                            @RequestBody CommandDto commandDto,
                            @AuthenticationPrincipal AuthUserDetails authUser) throws CommandException {
        return gameService.makeMove(gameId, authUser.toUser(), commandDto);
    }

}
