package com.lostcities.lostcities.entity;

import javax.persistence.*;

@Entity(name="PlayerEntity")
@Table(name="players")
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    public PlayerEntity() {

    }

    public PlayerEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
