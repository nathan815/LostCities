package com.lostcities.lostcities.repository;

import com.lostcities.lostcities.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
}
