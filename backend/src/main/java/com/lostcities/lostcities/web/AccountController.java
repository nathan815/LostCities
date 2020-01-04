package com.lostcities.lostcities.web;

import com.lostcities.lostcities.application.service.AccountService;
import com.lostcities.lostcities.application.dto.AccountCredentialsDto;
import com.lostcities.lostcities.application.dto.AuthenticationDto;
import com.lostcities.lostcities.application.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/authentication")
    public AuthenticationDto authentication(@RequestBody @Valid AccountCredentialsDto accountCredentials) {
        try {
            return accountService.authenticateWithCredentials(accountCredentials);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid UserDto userDto) {
        return accountService.createAccount(userDto);
    }
}
