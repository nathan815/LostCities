package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.Color;
import com.lostcities.lostcities.domain.game.card.Deck;
import com.lostcities.lostcities.domain.user.User;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToOne
    private User user;

    @Transient
    private Player player;

    enum Type {
        ReadyToStart,
        PlayCard,
        DiscardCard,
        DrawDeck,
        DrawDiscard;
    }
    private Type type;

    @Transient
    private Card playCard = null;

    @Transient
    private Card discardCard = null;

    @Transient
    private Color drawDiscardColor = null;

    public static class MoveBuilder {
        private Player player;
        private Card playCard;
        private Card discardCard;
        private Color drawDiscardColor;

        private MoveBuilder() {
        }

        public MoveBuilder player(Player player) {
            this.player = player;
            return this;
        }

        public MoveBuilder playCard(Card playCard) {
            this.playCard = playCard;
            return this;
        }

        public MoveBuilder discardCard(Card discardCard) {
            this.discardCard = discardCard;
            return this;
        }

        public MoveBuilder drawDiscardColor(Color drawDiscardColor) {
            this.drawDiscardColor = drawDiscardColor;
            return this;
        }

        public Move build() {
            return new Move(player, playCard, discardCard, drawDiscardColor);
        }
    }

    public static MoveBuilder builder() {
        return new MoveBuilder();
    }

    public Move() {
    }

    private Move(Player player, Card playCard, Card discardCard, Color drawDiscardColor) {
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

    public void setGame(Game game) {
        this.game = game;
    }

    protected void execute(Deck deck, GameBoard board) throws MoveException {
        if(deck.isEmpty()) {
            throw new EmptyDeckMoveException("Cannot start turn because deck is empty");
        }
        if(drawDiscardColor != null && board.getDiscardStack(drawDiscardColor).isEmpty()) {
            throw new MoveException("Cannot draw from " + drawDiscardColor +
                    " discard pile because it has no cards");
        }

        if(playCard != null) {
            player.play(playCard);
        } else if(discardCard != null) {
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
        cardOpt.ifPresent(player::addToHand);
    }

}
