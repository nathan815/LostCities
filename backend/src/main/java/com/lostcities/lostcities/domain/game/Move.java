package com.lostcities.lostcities.domain.game;

import com.lostcities.lostcities.domain.game.card.Card;
import com.lostcities.lostcities.domain.game.card.Color;
import com.lostcities.lostcities.domain.game.card.Deck;
import com.lostcities.lostcities.domain.game.exception.EmptyDeckException;
import com.lostcities.lostcities.domain.game.exception.EmptyDiscardException;
import com.lostcities.lostcities.domain.user.User;
import com.lostcities.lostcities.persistence.CardConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private Type type;

    @Convert(converter = CardConverter.class)
    private Card card = null;

    @Enumerated(EnumType.STRING)
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

    public String getDescription() {
        List<String> params = new ArrayList<>();
        if(color != null) params.add(color.toString());
        if(card != null) params.add(card.toString());

        return String.format("%s %s", type, params.isEmpty() ? "" : "(" + String.join(",", params) + ")");
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
        if(previousMove == null || isDrawingCardJustDiscarded(previousMove)) {
            return false;
        }

        boolean firstMoveOfTurn = previousMove.doesEndTurn() && this.type.stage.isFirstStage();
        boolean moveIsOneOrderAbovePrevious = this.type.stage.order - previousMove.type.stage.order == 1;
        return firstMoveOfTurn || moveIsOneOrderAbovePrevious;
    }

    private boolean isDrawingCardJustDiscarded(Move previousMove) {
        return previousMove.getType() == Type.DiscardCard && this.getType() == Type.DrawDiscard
                && this.getColor() == previousMove.getCard().getColor();
    }

    protected boolean doesTurnMatter() {
        return type != Type.ReadyToStart;
    }

    protected boolean allowedBeforeGameStarts() {
        return type.stage == TurnStage.PreStart;
    }

    protected boolean doesEndTurn() {
        return type.stage.isLastStage();
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

    public enum TurnStage {
        PreStart(0), PlayOrDiscard(1), Draw(2);

        private static final TurnStage FIRST_STAGE = TurnStage.PlayOrDiscard;
        private static final TurnStage LAST_STAGE = TurnStage.Draw;

        protected int order;

        TurnStage(int order) {
            this.order = order;
        }

        public boolean isFirstStage() {
            return order == FIRST_STAGE.order;
        }

        public boolean isLastStage() {
            return order == LAST_STAGE.order;
        }

        public TurnStage getFollowingStage() {
            for (TurnStage stage : values()) {
                if (stage.order > this.order) {
                    return stage;
                }
            }
            return FIRST_STAGE;
        }
    }

    public enum Type {
        ReadyToStart(TurnStage.PreStart),
        PlayCard(TurnStage.PlayOrDiscard),
        DiscardCard(TurnStage.PlayOrDiscard),
        DrawDeck(TurnStage.Draw),
        DrawDiscard(TurnStage.Draw);

        protected TurnStage stage;

        Type(TurnStage stage) {
            this.stage = stage;
        }

        public TurnStage getStage() {
            return stage;
        }
    }
}
