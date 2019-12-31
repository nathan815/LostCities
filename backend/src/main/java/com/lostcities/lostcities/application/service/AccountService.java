package com.lostcities.lostcities.application.service;

import com.lostcities.lostcities.presentation.web.security.JwtTokenHelper;
import com.lostcities.lostcities.persistence.entity.PlayerEntity;
import com.lostcities.lostcities.persistence.entity.UserEntity;
import com.lostcities.lostcities.persistence.repository.PlayerRepository;
import com.lostcities.lostcities.persistence.repository.UserRepository;
import com.lostcities.lostcities.application.dto.AccountCredentialsDto;
import com.lostcities.lostcities.application.dto.AuthenticationDto;
import com.lostcities.lostcities.application.dto.UserDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.ArrayList;

@Service
public class AccountService {

    BCryptPasswordEncoder passwordEncoder;
    UserRepository userRepository;
    PlayerRepository playerRepository;

    public AccountService(BCryptPasswordEncoder passwordEncoder,
                          UserRepository userRepository,
                          PlayerRepository playerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
    }

    public AuthenticationDto authenticateWithCredentials(AccountCredentialsDto accountCredentials)
            throws AuthenticationException {
        UserEntity userEntity = userRepository.findByUsername(accountCredentials.getUsername())
                .orElseThrow(() -> new AuthenticationException("Incorrect username"));

        if(!passwordEncoder.matches(accountCredentials.getPassword(), userEntity.getPassword())) {
            throw new AuthenticationException("Incorrect password");
        }

        User user = new User(
                accountCredentials.getUsername(),
                accountCredentials.getPassword(),
                new ArrayList<>()
        );

        String token = JwtTokenHelper.generateToken(
                new UsernamePasswordAuthenticationToken(user, null)
        );

        UserDto userDto = UserDto.fromUserEntity(userEntity);
        return new AuthenticationDto(token, userDto);
    }

    public UserDto createAccount(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setEmail(userDto.getEmail());

        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        userEntity.setPassword(encryptedPassword);

        userRepository.save(userEntity);

        PlayerEntity player = new PlayerEntity();
        player.setName(userDto.getUsername());
        player.setUser(userEntity);
        playerRepository.save(player);

        return userDto;
    }

}
