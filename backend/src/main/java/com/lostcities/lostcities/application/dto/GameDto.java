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
    public long currentTurnPlayerId;
    public List<PlayerDto> players;
    public GameBoardDto board;
    public Set<Card> hand;

    public GameDto(long id, int deckSize, Game.Status status, long currentTurnPlayerId, List<PlayerDto> players,
                   GameBoardDto board) {
        this.id = id;
        this.deckSize = deckSize;
        this.status = status;
        this.currentTurnPlayerId = currentTurnPlayerId;
        this.players = players;
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
                game.getDeck().size(),
                game.getStatus(),
                game.getCurrentTurnPlayer().getId(),
                game.getPlayersStream().map(PlayerDto::fromPlayer).collect(Collectors.toList()),
                GameBoardDto.fromGameBoard(game.getBoard())
        );
    }
}
