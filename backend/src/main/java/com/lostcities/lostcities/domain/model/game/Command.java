package com.lostcities.lostcities.domain.model.game;

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

    protected void execute(Deck deck, GameBoard board) {
        if(deck.isEmpty()) {
            throw new RuntimeException("Cannot take a turn when deck is empty");
        }

        if(playCard != null) {
            player.removeFromHand(playCard);
            board.addCardInPlay(player.getPlayerId(), playCard);
        } else if(discardCard != null) {
            player.removeFromHand(discardCard);
            board.addToDiscard(discardCard);
        }

        if(drawCardColor != null) {
            Card card = board.drawFromDiscard(drawCardColor)
                    .orElseThrow(() -> new RuntimeException("No cards in " + drawCardColor + " discard"));
            player.addToHand(card);
        } else {
            deck.draw().ifPresent(card -> player.addToHand(card));
        }
    }
}
