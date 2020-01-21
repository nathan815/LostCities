package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.Color;
import com.lostcities.lostcities.domain.game.card.Deck;
import com.lostcities.lostcities.domain.user.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

@Entity
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private Player player;

    public enum Type {
        ReadyToStart,
        PlayCard,
        DiscardCard,
        DrawDeck,
        DrawDiscard
    }
    private Type type;

    @Transient
    private Card card = null;

    @Transient
    private Color color = null;

    public Move() {
    }

    public Move(Player player, Type type, Card card, Color color) {
        this.player = player;
        this.type = type;
        this.card = card;
        this.color = color;
    }

    @PostLoad
    private void postLoad() {
        player = game.getPlayerById(user.getId()).orElse(null);
    }

    public Type getType() {
        return type;
    }

    public Player getPlayer() {
        return player;
    }

    public Card getCard() {
        return card;
    }

    public Color getColor() {
        return color;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    protected void execute(Deck deck, GameBoard board) {
        switch(type) {
            case ReadyToStart:
                player.setReadyToStart(true);
                break;
            case PlayCard:
                player.play(card);
                break;
            case DiscardCard:
                player.removeFromHand(card);
                board.addToDiscard(card);
                break;
            case DrawDiscard:
                Card drawnDiscardCard = board.drawFromDiscard(color)
                        .orElseThrow(() -> new EmptyDiscardException(color));
                player.addToHand(drawnDiscardCard);
                break;
            case DrawDeck:
                Card drawnDeckCard = deck.draw().orElseThrow(EmptyDeckException::new);
                player.addToHand(drawnDeckCard);
                break;
        }
    }

    public static Move create(Player player, Type type) {
        return new Move(player, type, null, null);
    }

    public static Move create(Player player, Type type, Card card) {
        return new Move(player, type, card, null);
    }

    public static Move create(Player player, Type type, Color color) {
        return new Move(player, type, null, color);
    }
}
