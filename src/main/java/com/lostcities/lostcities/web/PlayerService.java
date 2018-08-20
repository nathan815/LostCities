package com.lostcities.lostcities.web;

import com.lostcities.lostcities.entity.PlayerEntity;
import com.lostcities.lostcities.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping(value="/",method=RequestMethod.POST)
    public PlayerEntity createPlayer(@RequestParam String name) {
        PlayerEntity playerEntity = new PlayerEntity(name);
        return playerRepository.save(playerEntity);
    }

    @RequestMapping(value="/{id}")
    public PlayerEntity getPlayer(@PathVariable Long id) {
        return playerRepository.findById(id).get();
    }
}
