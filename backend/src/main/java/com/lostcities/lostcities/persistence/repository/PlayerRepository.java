package com.lostcities.lostcities.persistence.repository;

import com.lostcities.lostcities.persistence.entity.PlayerEntity;
import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlayerRepository extends CrudRepository<PlayerEntity, Long> {

    public Optional<PlayerEntity> getPlayerEntityByUserUsername(String username);
}
