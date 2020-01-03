package com.lostcities.lostcities.application.dto;

import com.lostcities.lostcities.domain.model.game.card.Card;
import com.lostcities.lostcities.domain.model.game.card.Deck;
import com.lostcities.lostcities.domain.model.game.card.Color;
import com.lostcities.lostcities.domain.model.game.Game;
import com.lostcities.lostcities.domain.model.game.Player;
import java.util.Map;
import java.util.Set;

public class PlayerViewDto {
    private Long gameId;
    private Long playerId;
    private Set<Card> hand;
    private Map<Color, Deck> discard;
    private Map<Color, Deck> playerPlayedCards;
    private Map<Color, Deck> opponentPlayedCards;

    public static PlayerViewDto createFromGame(Game game, Player currentPlayer) {

        PlayerViewDto playerViewDto = new PlayerViewDto();

        playerViewDto.setGameId(game.getId());
        playerViewDto.setPlayerId(currentPlayer.getId());

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

    public Map<Color, Deck> getDiscard() {
        return discard;
    }

    public void setDiscard(Map<Color, Deck> discard) {
        this.discard = discard;
    }

    public Map<Color, Deck> getPlayerPlayedCards() {
        return playerPlayedCards;
    }

    public void setPlayerPlayedCards(Map<Color, Deck> playerPlayedCards) {
        this.playerPlayedCards = playerPlayedCards;
    }

    public Map<Color, Deck> getOpponentPlayedCards() {
        return opponentPlayedCards;
    }

    public void setOpponentPlayedCards(Map<Color, Deck> opponentPlayedCards) {
        this.opponentPlayedCards = opponentPlayedCards;
    }
}
