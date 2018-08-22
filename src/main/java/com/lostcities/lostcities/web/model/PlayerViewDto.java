package com.lostcities.lostcities.web.model;

import com.google.common.collect.Multimap;
import com.lostcities.lostcities.game.Card;
import com.lostcities.lostcities.game.Color;
import com.lostcities.lostcities.game.Game;
import com.lostcities.lostcities.game.Player;

import java.util.LinkedHashSet;

public class PlayerViewDto {
    private Long gameId;
    private Long playerId;
    private LinkedHashSet<Card> hand;
    private Multimap<Color, Card> discard;
    private Multimap<Color, Card> playerPlayedCards;
    private Multimap<Color, Card> opponentPlayedCards;

    public static PlayerViewDto createFromGame(Game game, Player currentPlayer) {

        PlayerViewDto playerViewDto = new PlayerViewDto();

        playerViewDto.setGameId(game.getId());
        playerViewDto.setPlayerId(currentPlayer.getPlayerId());
        playerViewDto.setDiscard(game.getDiscard());
        playerViewDto.setPlayerPlayedCards(currentPlayer.getInPlay());

        Player opponent = currentPlayer.getOpponent();
        playerViewDto.setOpponentPlayedCards(opponent.getInPlay());

        return playerViewDto;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public LinkedHashSet<Card> getHand() {
        return hand;
    }

    public void setHand(LinkedHashSet<Card> hand) {
        this.hand = hand;
    }

    public Multimap<Color, Card> getDiscard() {
        return discard;
    }

    public void setDiscard(Multimap<Color, Card> discard) {
        this.discard = discard;
    }

    public Multimap<Color, Card> getPlayerPlayedCards() {
        return playerPlayedCards;
    }

    public void setPlayerPlayedCards(Multimap<Color, Card> playerPlayedCards) {
        this.playerPlayedCards = playerPlayedCards;
    }

    public Multimap<Color, Card> getOpponentPlayedCards() {
        return opponentPlayedCards;
    }

    public void setOpponentPlayedCards(Multimap<Color, Card> opponentPlayedCards) {
        this.opponentPlayedCards = opponentPlayedCards;
    }
}
