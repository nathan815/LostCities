package com.lostcities.lostcities.web;

import com.lostcities.lostcities.entity.PlayerEntity;
import com.lostcities.lostcities.repository.PlayerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/players")
public class PlayerService {

    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @RequestMapping(method=RequestMethod.POST)
    public PlayerEntity createPlayer(@RequestParam String name) {
        PlayerEntity playerEntity = new PlayerEntity(name);
        return playerRepository.save(playerEntity);
    }

    @RequestMapping(value="/{id}")
    public PlayerEntity getPlayer(@PathVariable Long id) {
        return playerRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );
    }
}
