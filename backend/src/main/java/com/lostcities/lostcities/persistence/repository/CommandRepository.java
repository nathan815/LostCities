package com.lostcities.lostcities.persistence.repository;

import com.lostcities.lostcities.persistence.entity.CommandEntity;
import org.springframework.data.repository.CrudRepository;

public interface CommandRepository extends CrudRepository<CommandEntity, Long> {
}
