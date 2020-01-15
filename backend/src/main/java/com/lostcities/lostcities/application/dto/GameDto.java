package com.lostcities.lostcities.application.dto;

import com.lostcities.lostcities.domain.game.Game;
import com.lostcities.lostcities.domain.game.card.Card;

/**
 * DTO for Game
 * Safe to show to users as it doesn't have the opponent's hand (as a full Game object would)
 * and only has the cards at top of the deck/board stacks
 */
public class GameDto {

    public long id;
    public PlayerDto player1;
    public PlayerDto player2;
    public Card deckTopCard;
    public GameBoardDto board;

    private GameDto(long id, PlayerDto player1, PlayerDto player2, Card deckTopCard, GameBoardDto board) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.deckTopCard = deckTopCard;
        this.board = board;
    }

    public static GameDto fromGame(Game game) {
        return new GameDto(game.getId(),
                game.getPlayer1() == null ? null : PlayerDto.fromPlayer(game.getPlayer1()),
                game.getPlayer2() == null ? null : PlayerDto.fromPlayer(game.getPlayer2()),
                game.getDeck().getTop().orElse(null),
                GameBoardDto.fromGameBoard(game.getBoard())
        );
    }
}
