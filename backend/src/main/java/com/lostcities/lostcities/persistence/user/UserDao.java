package com.lostcities.lostcities.persistence.user;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}
