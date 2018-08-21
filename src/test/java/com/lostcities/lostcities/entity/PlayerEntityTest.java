package com.lostcities.lostcities.entity;

import com.lostcities.lostcities.LostcitiesApplication;
import com.lostcities.lostcities.repository.PlayerRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=LostcitiesApplication.class)
public class PlayerEntityTest {

    @Autowired
    private PlayerRepository playerRepository;

    //@Test
    public void createPlayer() {
        PlayerEntity playerEntity = playerRepository.save(createBob());

        assertEquals(playerEntity.getName(), "Bob");
        assertNotNull(playerEntity.getId());
    }

    private PlayerEntity createBob(){
        PlayerEntity player = new PlayerEntity();
        player.setName("Bob");
        return player;
    }
}