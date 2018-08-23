package com.lostcities.lostcities.repository;

import com.lostcities.lostcities.entity.CommandEntity;
import org.springframework.data.repository.CrudRepository;

public interface CommandRepository extends CrudRepository<CommandEntity, Long> {
}
