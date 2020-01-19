package com.lostcities.lostcities.persistence.user;

import com.lostcities.lostcities.domain.user.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
