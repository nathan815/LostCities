package com.lostcities.lostcities.entity;

import javax.persistence.*;
import java.util.Random;

@Entity(name="GameEntity")
@Table(name="games")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="player1_id")
    private PlayerEntity player1;

    @OneToOne
    @JoinColumn(name="player2_id")
    private PlayerEntity player2;

    @Column
    private Long seed;

    private static Random random = new Random();

    public GameEntity() {

    }

    public static GameEntity createGame(PlayerEntity player1) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setSeed(random.nextLong());
        gameEntity.setPlayer1(player1);
        return gameEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlayerEntity getPlayer1() {
        return player1;
    }

    public void setPlayer1(PlayerEntity player1) {
        this.player1 = player1;
    }

    public PlayerEntity getPlayer2() {
        return player2;
    }

    public void setPlayer2(PlayerEntity player2) {
        this.player2 = player2;
    }

    public Long getSeed() {
        return seed;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }
}
