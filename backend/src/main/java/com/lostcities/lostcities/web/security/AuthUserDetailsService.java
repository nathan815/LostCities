package com.lostcities.lostcities.web.security;

import com.lostcities.lostcities.domain.user.User;
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
        User user = userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new AuthUserDetails(user.getId(), user.getUsername(), user.getPassword());
    }
}
