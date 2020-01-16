package com.lostcities.lostcities.application.dto;

import com.lostcities.lostcities.domain.game.Game;
import com.lostcities.lostcities.domain.game.card.Card;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for Game
 * Safe to send to client as it:
 * - doesn't have the opponent's hand (as a full Game object would)
 * - only contains size of deck so player can't cheat by looking at deck, e.g. by inspecting network requests
 */
public class GameDto {

    public long id;
    public int deckSize;
    public Game.Status status;
    public List<PlayerDto> players;
    public GameBoardDto board;
    public Set<Card> hand;

    private GameDto(long id, Game.Status status, List<PlayerDto> players, int deckSize, GameBoardDto board) {
        this.id = id;
        this.status = status;
        this.players = players;
        this.deckSize = deckSize;
        this.board = board;
        this.hand = Collections.emptySet();
    }

    public GameDto withHand(Set<Card> hand) {
        this.hand = hand;
        return this;
    }

    public static GameDto fromGame(Game game) {
        return new GameDto(
                game.getId(),
                game.getStatus(),
                game.getPlayersStream().map(PlayerDto::fromPlayer).collect(Collectors.toList()),
                game.getDeck().size(),
                GameBoardDto.fromGameBoard(game.getBoard())
        );
    }
}
