package com.lostcities.lostcities.entity;

import com.lostcities.lostcities.game.Color;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "commands")
public class CommandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "games_id")
    private GameEntity game;

    @OneToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    @Column(name = "play_card")
    private String play;

    @Column(name = "discard_card")
    private String discard;

    @Column(name = "draw_card")
    private Color draw;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public String getDiscard() {
        return discard;
    }

    public void setDiscard(String discard) {
        this.discard = discard;
    }

    public Color getDraw() {
        return draw;
    }

    public void setDraw(Color draw) {
        this.draw = draw;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
