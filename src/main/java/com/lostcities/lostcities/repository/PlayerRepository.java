package com.lostcities.lostcities.repository;

import com.lostcities.lostcities.entity.PlayerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlayerRepository extends CrudRepository<PlayerEntity, Long> {

    public Optional<PlayerEntity> getPlayerEntityByUserUsername(String username);
}
