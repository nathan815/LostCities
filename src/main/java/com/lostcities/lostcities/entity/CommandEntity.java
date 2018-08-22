package com.lostcities.lostcities.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="commands")
public class CommandEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="games_id")
    private GameEntity game;

    @OneToOne
    @JoinColumn(name="player_id")
    private PlayerEntity player;

    @Column(name="play_card")
    private String play;

    @Column(name="discard_card")
    private String discard;

    @Column(name="draw_card")
    private String draw;

    @Column(name="created_at")
    @CreationTimestamp
    private Date creationDate;
}
