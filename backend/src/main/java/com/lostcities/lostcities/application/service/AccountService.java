package com.lostcities.lostcities.application.service;

import com.lostcities.lostcities.application.dto.AccountCredentialsDto;
import com.lostcities.lostcities.application.dto.AuthenticationDto;
import com.lostcities.lostcities.application.dto.UserDto;
import com.lostcities.lostcities.domain.user.User;
import com.lostcities.lostcities.persistence.user.UserDao;
import com.lostcities.lostcities.web.security.AuthUser;
import com.lostcities.lostcities.web.security.JwtTokenHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class AccountService {

    PasswordEncoder passwordEncoder;
    UserDao userDao;

    public AccountService(PasswordEncoder passwordEncoder, UserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    public AuthenticationDto authenticateWithCredentials(AccountCredentialsDto accountCredentials)
            throws AuthenticationException {
        User user = userDao.findByUsername(accountCredentials.getUsername())
                .orElseThrow(() -> new AuthenticationException("Incorrect username"));

        if(!passwordEncoder.matches(accountCredentials.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Incorrect password");
        }

        AuthUser authUser = new AuthUser(user.getId(), user.getUsername(), user.getPassword());

        String token = JwtTokenHelper.generateToken(
                new UsernamePasswordAuthenticationToken(authUser, null)
        );

        return new AuthenticationDto(token, UserDto.fromUser(user));
    }

    public UserDto createAccount(UserDto userDto) {
        User user = new User(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        String hashedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(hashedPassword);

        userDao.save(user);

        return userDto;
    }

}
