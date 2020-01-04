package com.lostcities.lostcities.presentation.web.security;

import com.lostcities.lostcities.persistence.entity.UserEntity;
import com.lostcities.lostcities.persistence.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class AuthUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public AuthUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new AuthUserDetails(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword());
    }
}
