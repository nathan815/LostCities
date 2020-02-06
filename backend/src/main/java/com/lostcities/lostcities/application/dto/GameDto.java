package com.lostcities.lostcities.application.dto;

import com.lostcities.lostcities.domain.game.Game;
import com.lostcities.lostcities.domain.game.Move;
import com.lostcities.lostcities.domain.game.Player;
import com.lostcities.lostcities.domain.game.card.Card;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

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
    public List<MoveDto> moves;
    public BoardDto board;
    public Set<Card> hand;
    public Set<Move.Type> nextPossibleMoves;

    public GameDto(long id, int deckSize, Game.Status status, long currentTurnPlayerId, List<PlayerDto> players,
                   Set<Move.Type> nextPossibleMoves, List<MoveDto> moves, BoardDto board) {
        this.id = id;
        this.deckSize = deckSize;
        this.status = status;
        this.currentTurnPlayerId = currentTurnPlayerId;
        this.players = players;
        this.moves = moves;
        this.nextPossibleMoves = nextPossibleMoves;
        this.board = board;
        this.hand = Collections.emptySet();
    }

    public GameDto withHand(Set<Card> hand) {
        this.hand = hand;
        return this;
    }

    public static GameDto fromGame(Game game) {
        List<Move> moves = game.getMoves();
        Set<Move.Type> nextPossibleMoves = moves.size() > 0
                ? moves.get(game.getMoves().size() - 1).getNextPossibleMoveTypes()
                : Move.getStartingMoveTypes();

        return new GameDto(
                game.getId(),
                game.getDeck().size(),
                game.getStatus(),
                game.getCurrentTurnPlayer().map(Player::getId).orElse(0L),
                game.getPlayersStream().map(PlayerDto::fromPlayer).collect(toList()),
                nextPossibleMoves,
                moves.stream().map(MoveDto::fromMove).collect(toList()),
                BoardDto.fromGameBoard(game.getBoard())
        );
    }
}
