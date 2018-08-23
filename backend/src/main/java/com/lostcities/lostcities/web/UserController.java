package com.lostcities.lostcities.web;

import com.lostcities.lostcities.entity.PlayerEntity;
import com.lostcities.lostcities.entity.UserEntity;
import com.lostcities.lostcities.repository.PlayerRepository;
import com.lostcities.lostcities.repository.UserRepository;
import com.lostcities.lostcities.web.dto.UserDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private UserDetailsManager userDetailsManager;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    private PlayerRepository playerRepository;

    public UserController(
            UserRepository userRepository,
            JdbcUserDetailsManager userDetailsManager,
            BCryptPasswordEncoder passwordEncoder,
            PlayerRepository playerRepository) {
        this.userRepository = userRepository;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.playerRepository = playerRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password) {

        UserEntity user = userRepository.findByUsername(username);

        if(passwordEncoder.encode(password).equals(user.getPassword())) {

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    new User(username, password, authorities), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/home";
        }
        return "redirect:/login";k
    }

    @GetMapping("/signup")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUserAccount(
            @ModelAttribute("user") @Valid UserDto accountDto,
            BindingResult result, WebRequest request, Errors errors) {

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails user = new User(
                accountDto.getEmail(),
                passwordEncoder.encode(accountDto.getPassword()),
                authorities);

        userDetailsManager.createUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity userEntity =
                userRepository.findByUsername(accountDto.getEmail());

        PlayerEntity player = new PlayerEntity();
        player.setName(accountDto.getEmail());
        player.setUser(userEntity);
        playerRepository.save(player);


        return "redirect:/home";
    }
}
