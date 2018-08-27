package com.lostcities.lostcities.web;

import com.lostcities.lostcities.service.AccountService;
import com.lostcities.lostcities.web.dto.AccountCredentialsDto;
import com.lostcities.lostcities.web.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/authentication")
    public Map<String, String> authentication(@RequestBody @Valid AccountCredentialsDto accountCredentials) {
        try {
            String token = accountService.authenticateWithCredentials(accountCredentials);
            Map<String, String> result = new HashMap<>();
            result.put("token", token);
            return result;
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid UserDto userDto) {
        return accountService.createAccount(userDto);
    }
}
