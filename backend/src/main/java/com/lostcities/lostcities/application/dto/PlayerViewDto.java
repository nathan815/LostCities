package com.lostcities.lostcities.application.dto;

import com.lostcities.lostcities.domain.model.game.Card;
import com.lostcities.lostcities.domain.model.game.CardDeck;
import com.lostcities.lostcities.domain.model.game.Color;
import com.lostcities.lostcities.domain.model.game.Game;
import com.lostcities.lostcities.domain.model.game.Player;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class PlayerViewDto {
    private Long gameId;
    private Long playerId;
    private Set<Card> hand;
    private Map<Color, CardDeck> discard;
    private Map<Color, CardDeck> playerPlayedCards;
    private Map<Color, CardDeck> opponentPlayedCards;

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

    public Set<Card> getHand() {
        return hand;
    }

    public void setHand(Set<Card> hand) {
        this.hand = hand;
    }

    public Map<Color, CardDeck> getDiscard() {
        return discard;
    }

    public void setDiscard(Map<Color, CardDeck> discard) {
        this.discard = discard;
    }

    public Map<Color, CardDeck> getPlayerPlayedCards() {
        return playerPlayedCards;
    }

    public void setPlayerPlayedCards(Map<Color, CardDeck> playerPlayedCards) {
        this.playerPlayedCards = playerPlayedCards;
    }

    public Map<Color, CardDeck> getOpponentPlayedCards() {
        return opponentPlayedCards;
    }

    public void setOpponentPlayedCards(Map<Color, CardDeck> opponentPlayedCards) {
        this.opponentPlayedCards = opponentPlayedCards;
    }
}
