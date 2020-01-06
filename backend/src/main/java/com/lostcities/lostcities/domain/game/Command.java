package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.Color;
import com.lostcities.lostcities.domain.game.card.Deck;
import java.util.Optional;

public class Command {

    private final Player player;
    private final Card playCard;
    private final Card discardCard;
    private final Color drawDiscardColor;

    public static class CommandBuilder {
        private Player player;
        private Card playCard;
        private Card discardCard;
        private Color drawDiscardColor;

        public CommandBuilder player(Player player) {
            this.player = player;
            return this;
        }

        public CommandBuilder playCard(Card playCard) {
            this.playCard = playCard;
            return this;
        }

        public CommandBuilder discardCard(Card discardCard) {
            this.discardCard = discardCard;
            return this;
        }

        public CommandBuilder drawDiscardCardColor(Color drawCardColor) {
            this.drawDiscardColor = drawCardColor;
            return this;
        }

        public Command build() {
            return new Command(player, playCard, discardCard, drawDiscardColor);
        }
    }

    public static CommandBuilder builder() {
        return new CommandBuilder();
    }

    private Command(Player player, Card playCard, Card discardCard, Color drawDiscardColor) {
        if(playCard != null && discardCard != null) {
            throw new IllegalArgumentException("Cannot simultaneously play a card and discard a card");
        }
        if(playCard == null && discardCard == null) {
            throw new IllegalArgumentException("Must either play a card or discard a card");
        }
        if(discardCard != null && discardCard.getColor() == drawDiscardColor) {
            throw new IllegalArgumentException("Cannot draw the card just discarded");
        }
        this.player = player;
        this.playCard = playCard;
        this.discardCard = discardCard;
        this.drawDiscardColor = drawDiscardColor;
    }

    public Player getPlayer() {
        return player;
    }

    public Card getPlayCard() {
        return playCard;
    }

    public Card getDiscardCard() {
        return discardCard;
    }

    public Color getDrawDiscardColor() {
        return drawDiscardColor;
    }

    protected void execute(Deck deck, GameBoard board) throws CommandException {
        if(deck.isEmpty()) {
            throw new EmptyDeckCommandException("Cannot start turn because deck is empty");
        }
        if(drawDiscardColor != null && board.getDiscardStack(drawDiscardColor).isEmpty()) {
            throw new CommandException("Cannot draw from " + drawDiscardColor +
                    " discard pile because it has no cards");
        }

        if(playCard != null) {
            validateCardInHand(playCard);
            validateLegalPlayCard(board);
            player.removeFromHand(playCard);
            board.addCardInPlay(player.getId(), playCard);
        } else if(discardCard != null) {
            validateCardInHand(discardCard);
            player.removeFromHand(discardCard);
            board.addToDiscard(discardCard);
        }

        handleDrawCard(deck, board);
    }

    private void handleDrawCard(Deck deck, GameBoard board) {
        Optional<Card> cardOpt;
        if(drawDiscardColor != null) {
            cardOpt = board.drawFromDiscard(drawDiscardColor);
        } else {
            cardOpt = deck.draw();
        }
        cardOpt.ifPresent(card -> player.addToHand(card));
    }

    private void validateLegalPlayCard(GameBoard board) throws CannotPlayLowerValueCardCommandException {
        var topCardOpt = board.getInPlayCardStack(playCard.getColor(), player.getId()).getTop();
        if(topCardOpt.isPresent()) {
            var topCard = topCardOpt.get();
            if(playCard.getNumber() < topCard.getNumber()) {
                throw new CannotPlayLowerValueCardCommandException(playCard, topCard);
            }
        }
    }

    private void validateCardInHand(Card card) throws CardNotInHandCommandException {
        if(!player.hasCard(card)) {
            throw new CardNotInHandCommandException(card);
        }
    }

}
