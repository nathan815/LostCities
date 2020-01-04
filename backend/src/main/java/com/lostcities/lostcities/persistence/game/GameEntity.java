package com.lostcities.lostcities.persistence.game;

import javax.persistence.*;

import com.lostcities.lostcities.domain.game.Game;
import com.lostcities.lostcities.domain.game.Player;
import com.lostcities.lostcities.persistence.user.UserEntity;
import java.util.Random;

@Entity(name = "GameEntity")
@Table(name = "games")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_1_id")
    private UserEntity user1;

    @OneToOne
    @JoinColumn(name = "user_2_id")
    private UserEntity user2;

    @Column
    private Long seed;

    private static Random random = new Random();

    public GameEntity() {

    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser1() {
        return user1;
    }

    public void setUser1(UserEntity user1) {
        this.user1 = user1;
    }

    public UserEntity getUser2() {
        return user2;
    }

    public void setUser2(UserEntity user2) {
        this.user2 = user2;
    }

    public Long getSeed() {
        return seed;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }

    public Game toGame() {
        Player user1 = new Player(
                getUser1().getId(),
                getUser1().getUsername());
        Player user2 = new Player(
                getUser2().getId(),
                getUser2().getUsername());
        return Game.create(getId(), getSeed(), user1, user2);
    }
}
