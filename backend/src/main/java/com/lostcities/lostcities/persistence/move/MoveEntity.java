package com.lostcities.lostcities.persistence.move;

import com.lostcities.lostcities.domain.game.card.Color;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "moves")
public class MoveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "games_id")
    private long gameId;

    private long userId;

    @Column(name = "play_card")
    private String playCard;

    @Column(name = "discard_card")
    private String discardCard;

    @Column(name = "draw_card")
    private Color drawDiscardCardColor;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date creationDate;

    public MoveEntity() {
    }

    public MoveEntity(long gameId, long userId, String playCard, String drawCard, Color discardCardColor) {
        this.gameId = gameId;
        this.userId = userId;
        this.playCard = playCard;
        this.discardCard = drawCard;
        this.drawDiscardCardColor = discardCardColor;
    }

    public long getId() {
        return id;
    }

    public long getGame() {
        return gameId;
    }

    public long getUserId() {
        return userId;
    }

    public String getPlayCard() {
        return playCard;
    }

    public String getDiscardCard() {
        return discardCard;
    }

    public Color getDrawDiscardCardColor() {
        return drawDiscardCardColor;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
