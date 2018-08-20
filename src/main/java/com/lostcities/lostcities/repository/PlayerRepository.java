package com.lostcities.lostcities.repository;

import com.lostcities.lostcities.entity.PlayerEntity;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<PlayerEntity, Long> {
}
