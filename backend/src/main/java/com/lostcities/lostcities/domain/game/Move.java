package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.Color;
import com.lostcities.lostcities.domain.game.card.Deck;
import com.lostcities.lostcities.domain.user.User;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private Player player;

    public enum Type {
        ReadyToStart(0, false),
        PlayCard(1, false),
        DiscardCard(1, false),
        DrawDeck(2, true),
        DrawDiscard(2, true);

        protected int order;
        protected boolean endsTurn;

        Type(int order, boolean endsTurn) {
            this.order = order;
            this.endsTurn = endsTurn;
        }
    }
    private Type type;

    @Transient
    private Card card = null;

    @Transient
    private Color color = null;

    public Move() {
    }

    public Move(Player player, Type type, Card card, Color color) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(type);
        this.player = player;
        this.type = type;
        this.card = card;
        this.color = color;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Type getType() {
        return type;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
                if(player.isReadyToStart()) {
                    throw new IllegalStateException("Player already started");
                }
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
            default:
                throw new IllegalStateException("Unhandled move type");
        }
    }

    protected boolean canPlayAfter(Move previousMove) {
        if(allowedBeforeGameStarts()) {
            return true;
        }
        if(previousMove == null) {
            return false;
        }

        boolean firstMoveOfTurn = previousMove.doesEndTurn() && this.type.order == 1;
        boolean moveIsOneOrderAbovePrevious = this.type.order - previousMove.type.order == 1;
        return firstMoveOfTurn || moveIsOneOrderAbovePrevious;
    }

    protected boolean doesTurnMatter() {
        return type != Type.ReadyToStart;
    }

    protected boolean allowedBeforeGameStarts() {
        return type == Type.ReadyToStart;
    }

    protected boolean doesEndTurn() {
        return type.endsTurn;
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
