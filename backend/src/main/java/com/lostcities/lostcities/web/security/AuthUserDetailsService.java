package com.lostcities.lostcities.web.security;

import com.lostcities.lostcities.persistence.user.UserEntity;
import com.lostcities.lostcities.persistence.user.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsService implements UserDetailsService {
    private UserDao userDao;

    public AuthUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new AuthUser(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword());
    }
}
