package com.lostcities.lostcities.application.service;

import com.lostcities.lostcities.application.dto.AccountCredentialsDto;
import com.lostcities.lostcities.application.dto.AuthenticationDto;
import com.lostcities.lostcities.application.dto.UserDto;
import com.lostcities.lostcities.persistence.user.UserDao;
import com.lostcities.lostcities.persistence.user.UserEntity;
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
        UserEntity userEntity = userDao.findByUsername(accountCredentials.getUsername())
                .orElseThrow(() -> new AuthenticationException("Incorrect username"));

        if(!passwordEncoder.matches(accountCredentials.getPassword(), userEntity.getPassword())) {
            throw new AuthenticationException("Incorrect password");
        }

        AuthUser user = new AuthUser(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword());

        String token = JwtTokenHelper.generateToken(
                new UsernamePasswordAuthenticationToken(user, null)
        );

        return new AuthenticationDto(token, userEntity.toUserDto());
    }

    public UserDto createAccount(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setEmail(userDto.getEmail());

        String hashedPassword = passwordEncoder.encode(userDto.getPassword());
        userEntity.setPassword(hashedPassword);

        userDao.save(userEntity);

        return userDto;
    }

}
