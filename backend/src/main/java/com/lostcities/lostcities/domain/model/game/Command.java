package com.lostcities.lostcities.domain.model.game;

import com.lostcities.lostcities.domain.model.game.card.Card;
import com.lostcities.lostcities.domain.model.game.card.Color;
import com.lostcities.lostcities.domain.model.game.card.Deck;
import java.util.Optional;

public class Command {

    private Player player;
    private Card playCard;
    private Card discardCard;
    private Color drawCardColor;

    public Command(Player player, Card playCard, Card discardCard, Color drawCardColor) {
        if(playCard != null && discardCard != null) {
            throw new IllegalArgumentException("Cannot simultaneously play a card and discard a card");
        }
        if(playCard == null && discardCard == null) {
            throw new IllegalArgumentException("Must either play a card or discard a card");
        }
        if(discardCard != null && discardCard.getColor() == drawCardColor) {
            throw new IllegalArgumentException("Cannot draw the card just discarded");
        }
        this.player = player;
        this.playCard = playCard;
        this.discardCard = discardCard;
        this.drawCardColor = drawCardColor;
    }

    public Player getPlayer() {
        return player;
    }

    protected Card getPlayCard() {
        return playCard;
    }

    protected Card getDiscardCard() {
        return discardCard;
    }

    protected Color getDrawCardColor() {
        return drawCardColor;
    }

    protected void execute(Deck deck, GameBoard board) throws CommandException {
        if(deck.isEmpty()) {
            throw new EmptyDeckCommandException("Cannot start turn because deck is empty");
        }
        if(drawCardColor != null && board.getDiscardForColor(drawCardColor).isEmpty()) {
            throw new CommandException("Cannot draw because " + drawCardColor + " discard pile contains no cards");
        }

        if(playCard != null) {
            if(!player.hasCard(playCard)) {
                throw new CardNotInHandCommandException("Player does not have card to play: " + playCard);
            }
            player.removeFromHand(playCard);
            board.addCardInPlay(player.getId(), playCard);
        } else if(discardCard != null) {
            if(!player.hasCard(discardCard)) {
                throw new CardNotInHandCommandException("Player does not have card to discard: " + discardCard);
            }
            player.removeFromHand(discardCard);
            board.addToDiscard(discardCard);
        }

        handleDrawCard(deck, board);
    }

    private void handleDrawCard(Deck deck, GameBoard board) {
        Optional<Card> cardOpt;
        if(drawCardColor != null) {
            cardOpt = board.drawFromDiscard(drawCardColor);
        } else {
            cardOpt = deck.draw();
        }
        cardOpt.ifPresent(card -> player.addToHand(card));
    }

}
