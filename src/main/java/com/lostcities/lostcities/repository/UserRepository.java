package com.lostcities.lostcities.repository;

import com.lostcities.lostcities.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
