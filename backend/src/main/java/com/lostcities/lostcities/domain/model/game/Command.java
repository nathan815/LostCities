package com.lostcities.lostcities.domain.model.game;

public class Command {

    private Player player;
    private Card playCard;
    private Card discardCard;
    private Color drawCardColor;

    public Command(Player player, Card playCard, Card discardCard, Color drawCardColor) {
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

    protected void execute() {
        if(getPlayCard() != null && getDiscardCard() != null) {
            //todo: Create an exception
            throw new RuntimeException();
        } else if(getPlayCard() != null) {
            player.play(getPlayCard());
        } else if(getDiscardCard() != null) {
            player.discard(getDiscardCard());
        } else {
            //todo: Create an exception
            throw new RuntimeException();
        }

        if(getDrawCardColor() != null) {
            player.drawFromDiscard(getDrawCardColor());
        } else {
            player.draw();
        }
    }
}
